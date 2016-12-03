<c:forEach var="events" items="${events}">
  Owner Name: ${events.getOwnerName()} <br>
  Event ID: ${events.getEventId()} <br>
  Event Name: ${events.getEventName()} <br>
  Start Date/Time: ${events.getStartDateTime()} <br>
  End Date/Time: ${events.getEndDateTime()} <br>
  ${events.getData()} <br>
  <br>
</c:forEach>