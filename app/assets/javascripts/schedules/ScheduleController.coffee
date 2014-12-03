class ScheduleController

  constructor: (@$log, @$location, @ScheduleService) ->
    @$log.debug "constructing ScheduleController"
    @groupSchedule = []
    @smallGroupSchedule = []
    @pilatesSchedule = []
    @getClassSchedules()

  getClassSchedules: () ->
    @$log.debug "getClassSchedule(group)"
    @ScheduleService.getSchedule('group')
    .then(
      (data) =>
        @$log.debug "Promise returned #{data.length} getSchedule"
        @groupSchedule = data
      ,
      (error) =>
        @$log.error "Unable to get Schedule: ${error}"
    )

    @$log.debug "getClassSchedule(small_group)"
    @ScheduleService.getSchedule('small_group')
    .then(
      (data) =>
        @$log.debug "Promise returned #{data.length} getSchedule"
        @smallGroupSchedule = data
    ,
      (error) =>
        @$log.error "Unable to get Schedule: ${error}"
    )

    @$log.debug "getClassSchedule(pilates)"
    @ScheduleService.getSchedule('pilates')
    .then(
      (data) =>
        @$log.debug "Promise returned #{data.length} getSchedule"
        @pilatesSchedule = data
    ,
      (error) =>
        @$log.error "Unable to get Schedule: ${error}"
    )

  updateClassSchedule: (classType) ->
    @$log.debug "updateClassSchedule(#{classType})"

    if classType == 'group'
      @ScheduleService.updateSchedule(classType, @groupSchedule._id.$oid, @groupSchedule)
      .then(
        (data) =>
          @$log.debug "Promise returned #{data} Schedule"
          @groupSchedule = data
          @$location.path("/schedule/#{classType}")
        ,
        (error) =>
          @$log.error "Unable to update Schedule: #{error}"
      )
    else if classType == 'small_group'
      @ScheduleService.updateSchedule(classType, @groupSchedule._id.$oid, @smallGroupSchedule)
      .then(
        (data) =>
          @$log.debug "Promise returned #{data} Schedule"
          @smallGroupSchedule = data
          @$location.path("/schedule/#{classType}")
      ,
        (error) =>
          @$log.error "Unable to update Schedule: #{error}"
      )
    else
      @ScheduleService.updateSchedule(classType, @pilatesSchedule._id.$oid, @pilatesSchedule)
      .then(
        (data) =>
          @$log.debug "Promise returned #{data} Schedule"
          @pilatesSchedule = data
          @$location.path("/schedule/#{classType}")
      ,
        (error) =>
          @$log.error "Unable to update Schedule: #{error}"
      )

controllersModule.controller('ScheduleController', ScheduleController)