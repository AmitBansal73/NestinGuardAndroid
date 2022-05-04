package com.anvisys.nestinguard.Common;

public class Visitor {

    public String VisitorName, VisitorMobile, VisitorAddress,VisitPurpose, FlatNumber, ActualInTime,ExpectedEndTime,StartTime,EndTime;

    public String getVisitorName() {
        return VisitorName;
    }

    public void setVisitorName(String visitorName) {
        VisitorName = visitorName;
    }

    public String getVisitorMobile() {
        return VisitorMobile;
    }

    public void setVisitorMobile(String visitorMobile) {
        VisitorMobile = visitorMobile;
    }

    public String getVisitorAddress() {
        return VisitorAddress;
    }

    public void setVisitorAddress(String visitorAddress) {
        VisitorAddress = visitorAddress;
    }

    public String getVisitPurpose() {
        return VisitPurpose;
    }

    public void setVisitPurpose(String visitPurpose) {
        VisitPurpose = visitPurpose;
    }

    public String getFlatNumber() {
        return FlatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        FlatNumber = flatNumber;
    }

    public String getActualInTime() {
        return ActualInTime;
    }

    public void setActualInTime(String actualInTime) {
        ActualInTime = actualInTime;
    }

    public String getExpectedEndTime() {
        return ExpectedEndTime;
    }

    public void setExpectedEndTime(String expectedEndTime) {
        ExpectedEndTime = expectedEndTime;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getResidentMobile() {
        return ResidentMobile;
    }

    public void setResidentMobile(String residentMobile) {
        ResidentMobile = residentMobile;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getRequestId() {
        return RequestId;
    }

    public void setRequestId(int requestId) {
        RequestId = requestId;
    }

    public  String FirstName, LastName,  ResidentMobile,  Type;
    public  int RequestId;

}
