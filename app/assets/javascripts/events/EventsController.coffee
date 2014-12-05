class EventsController

  constructor: (@$log, @$location, @EventsService) ->
    @$log.debug "constructing EventsController"
    @events = []
    @getEvents()

  getEvents: () ->
    @$log.debug "getEvents()"
    @EventsService.getEvents()
      .then(
        (data) =>
          @$log.debug "Promise returned - getEvents()"
          @events = data
        ,
        (error) =>
          @$log.error "Unable to getEvents: ${error}"
    )

  updateEvents: () ->
    @$log.debug "updateEvents()"
    @EventsService.updateEvents(@events)
      .then(
        (data) =>
          @$log.debug "Promise returned #{data} Events"
          @events = data
          @$location.path("/events")
        ,
        (error) =>
          @$log.error "Unable to updateEvents: #{error}"
    )

  addEvent: () ->
    newEvent = { "name":'', "date":'', "location":'',
    "description":'', "contact":'' }
    @events.eventSchedule.splice(@events.eventSchedule.length, 0, newEvent)

  moveEvent: (direction, index) ->
    if direction == "up"
      if index > 0
        temp = @events.eventSchedule[index - 1]
        @events.eventSchedule[index - 1] = @events.eventSchedule[index]
        @events.eventSchedule[index] = temp
    else if direction == "down"
      if index < @events.eventSchedule.length - 1
        temp = @events.eventSchedule[index + 1]
        @events.eventSchedule[index + 1] = @events.eventSchedule[index]
        @events.eventSchedule[index] = temp
    else
      alert("Error: Please try again or contact the web administrator!")
      @$log.error("Wrong direction provided, could not move events")

  removeEvent: (index) ->
    @events.eventSchedule.splice(index, 1)

controllersModule.controller('EventsController', EventsController)