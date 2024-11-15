package ru.mikhail.lab3.beans

import jakarta.enterprise.context.SessionScoped
import jakarta.inject.Inject
import jakarta.inject.Named
import ru.mikhail.lab3.dbobjects.Result
import java.io.Serializable
import ru.mikhail.lab3.DotChecker.checkDot
import java.sql.Timestamp
import java.time.LocalDateTime

@Named("dotCheckerBean")
@SessionScoped
open class DotCheckerBean : IDotCheckerBean, Serializable { // Реализуем интерфейс

    companion object {
        private const val MIN_EXECUTION_TIME_NS = 1L
    }

    @Inject
    private lateinit var controllerBean: IControllerBean // Инжектируем интерфейс

    override fun checkAndCalculatePoint(): Result {
        val startTime = System.nanoTime()
        val result = checkDot(controllerBean.x, controllerBean.y, controllerBean.r)
        val endTime = System.nanoTime()
        val executionTime = maxOf(endTime - startTime, MIN_EXECUTION_TIME_NS)
        val now = Timestamp.valueOf(LocalDateTime.now())

        return Result(controllerBean.x, controllerBean.y, controllerBean.r, result, executionTime, now)
    }
}
