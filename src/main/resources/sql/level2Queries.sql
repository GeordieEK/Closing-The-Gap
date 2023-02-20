
-- Basic view joining all demographics to demographic table.
SELECT lga_code AS 'LGA Code', status_description AS 'Indigenous Status', sex_description AS 'Sex', category AS 'Employment Status', num_people AS 'No. People' FROM Demographic
JOIN Age ON demographic_id = Age.id
JOIN Gender ON sex_id = sex
JOIN Indigenous_Status ON indi_status = status

--Total population Australia
SELECT SUM(num_people) FROM Demographic
JOIN Age ON demographic_id = Age.id
JOIN Gender ON sex_id = sex
JOIN Indigenous_Status ON indi_status = status
JOIN Age_Bandwidth ON category = age_bandwidth.bandwidth_age;

--Total population in a state (given by LGA code number range, this can be passed in as a variable)
SELECT SUM(num_people) FROM Demographic
JOIN Age ON demographic_id = Age.id
JOIN Gender ON sex_id = sex
JOIN Indigenous_Status ON indi_status = status
JOIN Age_Bandwidth ON category = age_bandwidth.bandwidth_age
WHERE lga_code > 0 AND lga_code < 20000;

--Total population in an LGA (given by exact LGA code number that can be passed in as a variable)
SELECT lga_code, SUM(num_people) FROM Demographic
JOIN Age ON demographic_id = Age.id
JOIN Gender ON sex_id = sex
JOIN Indigenous_Status ON indi_status = status
JOIN Age_Bandwidth ON category = age_bandwidth.bandwidth_age
WHERE lga_code = 10050
GROUP BY lga_code;

-- Total number indigenous people over 65
SELECT SUM(num_people) FROM Demographic
JOIN Age ON demographic_id = Age.id
JOIN Gender ON sex_id = sex
JOIN Indigenous_Status ON indi_status = status
JOIN Age_Bandwidth ON category = age_bandwidth.bandwidth_age
WHERE indi_status = 'i' AND lower_limit = 65
GROUP BY bandwidth_age;


--Compared to non-Indigenous people, what proportion of Indigenous people are aged 65 or over as a percentage of the total Indigenous population?

-- Total number indigenous people over 65, as a percentage of total number indigenous population 
-- Then total > 65 divided by total indig population * 100
SELECT SUM(num_people) AS query_population, total_population, (SUM(num_people) / CAST(total_population AS REAL) * 100) query_result FROM Demographic
JOIN Age ON demographic_id = Age.id
JOIN Gender ON sex_id = sex
JOIN Indigenous_Status ON indi_status = status
JOIN Age_Bandwidth ON category = age_bandwidth.bandwidth_age
JOIN (
    --Total indigenous population
    SELECT SUM(num_people) AS total_population FROM Demographic
    JOIN Age ON demographic_id = Age.id
    JOIN Gender ON sex_id = sex
    JOIN Indigenous_Status ON indi_status = status
    WHERE indi_status = 'i'
)
WHERE indi_status = 'i' AND lower_limit = 65
GROUP BY bandwidth_age

-- Look up Age by percentage of LGA population
SELECT demographic.lga_code, lga.lga_name, status_description, sex_description
, SUM(CASE
    WHEN category = '0 - 4' THEN ((((num_people / CAST(totalPeople AS REAL)) * 100) / CAST(totalPeople AS REAL)) * 100)
    END) AS '0 - 4'
, SUM(CASE
    WHEN category = '5 - 9' THEN ((((num_people / CAST(totalPeople AS REAL)) * 100) / CAST(totalPeople AS REAL)) * 100)
    END) AS '5 - 9'
, SUM(CASE 
    WHEN category = '10 - 14' THEN ((((num_people / CAST(totalPeople AS REAL)) * 100) / CAST(totalPeople AS REAL)) * 100)
    END) AS '10 - 14'
, SUM(CASE
    WHEN category = '15 - 19' THEN ((num_people / CAST(totalPeople AS REAL)) * 100)
    END) AS '15 - 19'
, SUM(CASE
    WHEN category = '20 - 24' THEN ((num_people / CAST(totalPeople AS REAL)) * 100)
    END) AS '20 - 24'
, SUM(CASE
    WHEN category = '25 - 29' THEN ((num_people / CAST(totalPeople AS REAL)) * 100)
    END) AS '25 - 29'
, SUM(CASE
    WHEN category = '30 - 34' THEN ((num_people / CAST(totalPeople AS REAL)) * 100)
    END) AS '30 - 34'
, SUM(CASE
    WHEN category = '35 - 39' THEN ((num_people / CAST(totalPeople AS REAL)) * 100)
    END) AS '35 - 39'
, SUM(CASE
    WHEN category = '40 - 44' THEN ((num_people / CAST(totalPeople AS REAL)) * 100)
    END) AS '40 - 44'
, SUM(CASE
    WHEN category = '45 - 49' THEN ((num_people / CAST(totalPeople AS REAL)) * 100)
    END) AS '45 - 49'
, SUM(CASE
    WHEN category = '50 - 54' THEN ((num_people / CAST(totalPeople AS REAL)) * 100)
    END) AS '50 - 54'
, SUM(CASE
    WHEN category = '55 - 59' THEN ((num_people / CAST(totalPeople AS REAL)) * 100)
    END) AS '55 - 59'
, SUM(CASE
    WHEN category = '60 - 64' THEN ((num_people / CAST(totalPeople AS REAL)) * 100)
    END) AS '60 - 64'
, SUM(CASE
    WHEN category = '65+' THEN ((num_people / CAST(totalPeople AS REAL)) * 100)
    END) AS '65+'
FROM Demographic
JOIN Age ON demographic_id = Age.id
JOIN Gender ON sex_id = sex
JOIN Indigenous_Status ON indi_status = status
JOIN LGA ON lga.code = demographic.lga_code
JOIN (
--Number of people in each LGA grouped by Indigenous Status
SELECT lga_code, status_description, SUM(num_people) AS totalPeople FROM Demographic
JOIN Age ON demographic_id = Age.id
JOIN Gender ON sex_id = sex
JOIN Indigenous_Status ON indi_status = status
GROUP BY lga_code, status_description
) AS total_pop ON total_pop.lga_code = demographic.lga_code AND total_pop.status_description = indigenous_status.status_description
GROUP BY demographic.lga_code, indigenous_status.status_description, gender.sex_description

-- LGAs sorted by Employment, School or Age
-- Sorted by age
SELECT * FROM (
SELECT demographic.lga_code
, (SUM(CASE
    WHEN category = '65+' AND indigenous_status.status_description = 'Indigenous' THEN CAST(num_people AS REAL)
    END) / CAST(totalPeople AS REAL)) * 100 AS 'ranking_metric'
FROM Demographic
JOIN Age ON demographic_id = Age.id
JOIN LGA ON lga.code = demographic.lga_code
JOIN Indigenous_Status ON indi_status = indigenous_status.status
JOIN (
    --Number of people in each LGA grouped by Indigenous Status
    SELECT lga_code, status_description, SUM(num_people) AS totalPeople FROM Demographic
    JOIN Age ON demographic_id = Age.id
    JOIN Indigenous_Status ON indi_status = status
    GROUP BY lga_code, status_description
    ) 
AS total_pop ON total_pop.lga_code = demographic.lga_code AND total_pop.status_description = indigenous_status.status_description
GROUP BY demographic.lga_code, indigenous_status.status_description
ORDER BY ranking_metric DESC
)
AS sorted_lga 
JOIN lga ON sorted_lga.lga_code = lga.code
ORDER BY ranking_metric DESC

-- Sorted by year 12 completion
SELECT * FROM (
SELECT demographic.lga_code
, SUM(CASE
    WHEN indigenous_status.status_description = 'Indigenous' AND category = 'Year 12' THEN ((num_people / CAST(totalPeople AS REAL)) * 100)
    END) AS 'ranking_metric'
FROM Demographic
JOIN school_education ON demographic_id = school_education.id
JOIN Indigenous_Status ON indi_status = status
JOIN LGA ON lga.code = demographic.lga_code
JOIN (
--Number of people in each LGA grouped by Indigenous Status
SELECT lga_code, status_description, SUM(num_people) AS totalPeople FROM Demographic
JOIN school_education ON demographic_id = school_education.id
JOIN Indigenous_Status ON indi_status = status
GROUP BY lga_code, status_description
) AS total_pop ON total_pop.lga_code = demographic.lga_code AND total_pop.status_description = indigenous_status.status_description
GROUP BY demographic.lga_code, indigenous_status.status_description
ORDER BY ranking_metric DESC
)
AS sorted_lga 
JOIN lga ON sorted_lga.lga_code = lga.code
ORDER BY ranking_metric DESC

-- Sorted by employment
SELECT * FROM (
SELECT demographic.lga_code, lga.lga_name, indigenous_status.status_description, gender.sex_description
, (SUM(CASE
    WHEN category = 'employed' AND indigenous_status.status_description = 'Indigenous' THEN num_people
    END) / SUM(CAST(totalPeople AS REAL)) * 100)  AS 'indigenous_employed'
FROM Demographic
JOIN Labour_Force ON demographic_id = Labour_Force.id
JOIN Gender ON sex_id = sex
JOIN Indigenous_Status ON indi_status = status
JOIN LGA ON demographic.lga_code = LGA.code
JOIN (
--Number of people in each LGA grouped by Indigenous Status
SELECT demographic.lga_code, status_description, SUM(num_people) AS totalPeople FROM Demographic
JOIN Labour_force ON demographic_id = Labour_Force.id
JOIN Gender ON sex_id = sex
JOIN Indigenous_Status ON indi_status = status
GROUP BY demographic.lga_code, indigenous_status.status_description
) AS total_pop ON total_pop.lga_code = demographic.lga_code AND total_pop.status_description = indigenous_status.status_description
GROUP BY demographic.lga_code, indigenous_status.status_description, gender.sex_description
ORDER BY indigenous_employed DESC
) 
AS sorted_lga 

JOIN lga ON sorted_lga.lga_code = lga.code

--Sorted by highest percentage qualifications bachelor degree > higher
SELECT * FROM (
SELECT demographic.lga_code
, SUM(CASE
    WHEN indigenous_status.status_description = 'Indigenous' AND (category = 'Bachelor Degree' OR 'Graduate Diploma/Certificate' OR 'Postgraduate Degree') THEN ((num_people / CAST(totalPeople AS REAL)) * 100)
    END) AS 'ranking_metric'
FROM Demographic
JOIN non_school_education ON demographic_id = non_school_education.id
JOIN Indigenous_Status ON indi_status = status
JOIN LGA ON lga.code = demographic.lga_code
JOIN (
--Number of people in each LGA grouped by Indigenous Status
SELECT lga_code, status_description, SUM(num_people) AS totalPeople FROM Demographic
JOIN non_school_education ON demographic_id = non_school_education.id
JOIN Indigenous_Status ON indi_status = status
GROUP BY lga_code, status_description
) AS total_pop ON total_pop.lga_code = demographic.lga_code AND total_pop.status_description = indigenous_status.status_description
GROUP BY demographic.lga_code, indigenous_status.status_description
ORDER BY ranking_metric DESC
)
AS sorted_lga 
JOIN lga ON sorted_lga.lga_code = lga.code
ORDER BY ranking_metric DESC