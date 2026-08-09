import io.github.runkang10.fixedGameMode.services.Configuration
import io.github.runkang10.fixedGameMode.services.IConfiguration
import org.junit.jupiter.api.Test
import org.spongepowered.configurate.ConfigurationOptions
import org.spongepowered.configurate.NodePath
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment
import org.spongepowered.configurate.transformation.ConfigurationTransformation
import org.spongepowered.configurate.transformation.TransformAction
import java.io.File

class ConfigurationTest {
    @ConfigSerializable
    data class Data(
        @Comment("HIIIII")
        val isTyped: Boolean = true,
        val version: Int = 2
    )

    @Test
    fun loadingTest() {
        val file = File("test.conf")
        val configuration = Configuration(
            file,
            Data::class,
            Data(),
            ConfigurationOptions.defaults().header("HEADER\nIs it?"),
            ConfigurationTransformation.versionedBuilder()
                .versionKey("version")
                .addVersion(
                    5, ConfigurationTransformation.builder()
                        .addAction(NodePath.path("typed"), TransformAction.rename("what"))
                        .build()
                )
                .build()
        )
        when (val result = configuration.load()) {
            is IConfiguration.Result.Success<*> -> {
                print("Success migrated=${result.migrated}!")
            }

            is IConfiguration.Result.Failure -> {
                print(result.error)
            }
        }
    }
}