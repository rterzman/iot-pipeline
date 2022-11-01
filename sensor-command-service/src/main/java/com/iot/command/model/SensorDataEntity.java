package com.iot.command.model;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Table(schema = "sensors", name = "event_data")
public class SensorDataEntity implements Persistable<UUID> {
    @Id
    private UUID id;
    @NonNull
    @Column
    private String sensor;
    @NonNull
    @Column
    private ZonedDateTime ts;
    @NonNull
    @Column("measurement")
    private Double value;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
