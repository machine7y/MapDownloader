package machine7y.mapdownloader.domain.base.usecase

abstract class BaseNoParamsUseCase<out Result> {

    suspend operator fun invoke(): Result {
        return execute()
    }

    protected abstract suspend fun execute(): Result
}
