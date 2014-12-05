class ScheduleController

  constructor: (@$log, @$location, @ScheduleService) ->
    @$log.debug "constructing ScheduleController"
    @schedule = []
    @getClassSchedules()

  getClassSchedules: () ->
    classType = @$location.path().substring(1)
    @$log.debug "getClassSchedule(#{classType})"
    @ScheduleService.getSchedule(classType)
      .then(
        (data) =>
          @$log.debug "Promise returned getSchedule"
          @schedule = data
        ,
        (error) =>
          @$log.error "Unable to get Schedule: ${error}"
      )

  updateClassSchedule: () ->
    classType = @$location.path().substring(1)
    @$log.debug "updateClassSchedule(#{classType})"
    @ScheduleService.updateSchedule(classType, @schedule)
    .then(
      (data) =>
        @$log.debug "Promise returned #{data} Schedule"
        @schedule = data
        @$location.path("/schedule/#{classType}")
      ,
      (error) =>
        @$log.error "Unable to update Schedule: #{error}"
    )

  addClass: (addDay) ->
    newClass = { "name": '', "time": '', "instructor": '', "room": '' }
    for day, dayIndex in @schedule.classSchedule
      if day.day == addDay
        @schedule.classSchedule[dayIndex].classes
         .splice(@schedule.classSchedule[dayIndex].classes.length, 0, newClass)

  moveRow: (direction, moveDay, index) ->
    if direction == "up"
      for day, dayIndex in @schedule.classSchedule
        if day.day == moveDay
          if index > 0
            temp = @schedule.classSchedule[dayIndex].classes[index - 1]
            @schedule.classSchedule[dayIndex].classes[index - 1] = @schedule.classSchedule[dayIndex].classes[index]
            @schedule.classSchedule[dayIndex].classes[index] = temp
          break
    else if direction == "down"
      for day, dayIndex in @schedule.classSchedule
        if day.day == moveDay
          if index < @schedule.classSchedule[dayIndex].classes.length - 1
            temp = @schedule.classSchedule[dayIndex].classes[index + 1]
            @schedule.classSchedule[dayIndex].classes[index + 1] = @schedule.classSchedule[dayIndex].classes[index]
            @schedule.classSchedule[dayIndex].classes[index] = temp
          break
    else
      alert("Something wrong happened! Please try again or contact the web administrator!")
      @$log.error("Wrong direction provided, could not move classes")

  removeClass: (removeDay, index) ->
    for day, dayIndex in @schedule.classSchedule
      if day.day == removeDay
        @schedule.classSchedule[dayIndex].classes.splice(index, 1)
        break

controllersModule.controller('ScheduleController', ScheduleController)