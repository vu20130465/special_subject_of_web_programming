package vn.edu.hcmuaf.st.SmartphoneStore.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity {
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
