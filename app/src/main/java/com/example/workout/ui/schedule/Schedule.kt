package com.example.workout.ui.schedule

class Schedule {
    var id: Int = 0
    var typeSport: String? = null
    var typeSchedule: String? = null
    var scheduleDate: String? = null
    var startTime: String? = null
    var endTime: String? = null
    var repeatedDay: String? = null
    var autoStart: String? = null
    var targetSport: String? = null

    constructor(typeSport: String, typeSchedule: String, scheduleDate: String, startTime: String, endTime: String,
                repeatedDay: String, autoStart: String, targetSport: String) {
        this.typeSport = typeSport
        this.typeSchedule = typeSchedule
        this.scheduleDate = scheduleDate
        this.startTime = startTime
        this.endTime = endTime
        this.repeatedDay = repeatedDay
        this.autoStart = autoStart
        this.targetSport = targetSport
    }
}