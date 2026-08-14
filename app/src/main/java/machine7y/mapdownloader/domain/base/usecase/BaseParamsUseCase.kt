package machine7y.mapdownloader.domain.base.usecase

abstract class BaseParamsUseCase<in Param, out Result> {

    suspend operator fun invoke(param: Param): Result {
        return execute(param)
    }

    protected abstract suspend fun execute(param: Param): Result
}
