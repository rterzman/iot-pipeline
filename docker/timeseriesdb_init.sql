CREATE DATABASE iot;
CREATE USER iot_user WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE "iot" to iot_user;
\connect iot
-----------------------
CREATE SCHEMA sensors;
CREATE EXTENSION "uuid-ossp" SCHEMA public;
-----------------------
CREATE TABLE IF NOT EXISTS sensors.event_data
(
    id UUID DEFAULT uuid_generate_v4() NOT NULL,
    sensor varchar(255) NOT NULL,
    ts timestamp with time zone NOT NULL,
    measurement numeric NOT NULL
);
SELECT create_hypertable('sensors.event_data', 'ts', chunk_time_interval => 86400000000, if_not_exists => TRUE);
------------------------
GRANT ALL PRIVILEGES ON SCHEMA sensors TO iot_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA sensors TO iot_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA sensors to iot_user;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA sensors to iot_user;
------------------------
insert into sensors.event_data(sensor, ts, measurement)
values ('thermostat_HBkBFRlFHk', NOW() - INTERVAL '1 DAY', 75.5),
       ('thermostat_HBkBFRlFHk', NOW() - INTERVAL '1 MINUTES', 60),
       ('thermostat_HBkBFRlFHk', NOW() - INTERVAL '2 MINUTES', 60),
       ('thermostat_HBkBFRlFHk', NOW() - INTERVAL '3 MINUTES', 58),
       ('thermostat_HBkBFRlFHk', NOW() - INTERVAL '4 MINUTES', 56),
       ('thermostat_HBkBFRlFHk', NOW() - INTERVAL '5 MINUTES', 54),
       ('hearrate_Zk8NGiLi8L', NOW() - INTERVAL '5 MINUTES', 90),
       ('hearrate_Zk8NGiLi8L', NOW() - INTERVAL '4 MINUTES', 85),
       ('hearrate_Zk8NGiLi8L', NOW() - INTERVAL '3 MINUTES', 80),
       ('hearrate_Zk8NGiLi8L', NOW() - INTERVAL '1 MINUTES', 70);