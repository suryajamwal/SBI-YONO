package yono.dto;

import jakarta.validation.constraints.NotNull;
import yono.enums.ServiceType;

public class ServiceRequest {

    @NotNull(message = "Service type is required")
    private ServiceType serviceType;

    public ServiceRequest() {
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }
}