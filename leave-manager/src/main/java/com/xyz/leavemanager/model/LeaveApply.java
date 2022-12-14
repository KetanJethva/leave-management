package com.xyz.leavemanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({
        "empId",
        "appliedLeaves"
})
public class LeaveApply{

    @JsonProperty("empId")
    private int empId;
    @JsonProperty("appliedLeaves")
    private int appliedLeaves;

}
