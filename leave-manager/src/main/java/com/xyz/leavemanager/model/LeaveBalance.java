package com.xyz.leavemanager.model;

import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        "empId",
        "name",
        "leavesTaken",
        "availableLeaves"
})
public class LeaveBalance{

    @JsonProperty("empId")
    private int empId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("leavesTaken")
    private int leavesTaken;
    @JsonProperty("availableLeaves")
    private int availableLeaves;

}
