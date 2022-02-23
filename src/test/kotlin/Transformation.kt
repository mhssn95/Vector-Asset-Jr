import model.Transform
import model.elements.Path
import org.junit.Test

class Transformation {

    @Test
    fun `transform path to with x y`() {
        val path1 = Path(
            listOf(
                Path.Action.Move(40.0, 55.0)
            )
        )
        val path2 = Path(
            listOf(
                Path.Action.Move(20.0, 5.0)
            ), transform = listOf(Transform.Translate(20.0, 50.0))
        )
        assert(path1.actions == path2.getTransformedPath().actions)
    }

    @Test
    fun `scale path with x y`() {
        val path = Path(
            listOf(
                Path.Action.Move(20.0, 40.0)
            ),
            transform = listOf(Transform.Scale(2.0, 1.0))
        )
        println(path.getTransformedPath())
    }

    @Test
    fun `rotate path with theta x y`() {
        val path = Path(
            listOf(
                Path.Action.Move(3.0, 7.0)
            ),
            transform = listOf(Transform.Rotate(45.0, 30.0, 40.0))
        )
        println(path.getTransformedPath())
    }

}