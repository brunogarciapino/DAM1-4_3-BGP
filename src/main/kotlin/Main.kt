package un4.eje4_3

data class Tienda(val nombre: String, val clientes: List<Clientes>) {
    fun obtenerConjuntoDeClientes(): Set<Clientes> = clientes.toSet()
    fun obtenerCiudadesDeClientes(): Set<Ciudad> = clientes.map { it.ciudad }.toSet()
    fun obtenerClientesPor(ciudad:Ciudad): List<Clientes> = clientes.filter { it.ciudad == ciudad  }
    fun checkTodosClientesSonDe(ciudad: Ciudad): Boolean = clientes.all {it.ciudad == ciudad  }
    fun hayClientesDe(ciudad: Ciudad): Boolean = clientes.any {it.ciudad==ciudad}
    fun cuentaClientesDe(ciudad:Ciudad): Int = clientes.count { it.ciudad==ciudad }
    fun encuentraClienteDe(ciudad: Ciudad): Clientes? = clientes.find { it.ciudad == ciudad }
    fun obtenerClientesOrdenadosPorPedidos(): List<Clientes> = clientes.sortedByDescending { it.pedidos.size}
    fun obtenerClientesConPedidosSinEntregar(): Set<Clientes> = clientes.partition { clientes -> clientes.pedidos.count{it.estaEntregado} < clientes.pedidos.count { !it.estaEntregado }}.first.toSet()
    fun obtenerProductosPedidos(): Set<Producto> = clientes.flatMap{ it.pedidos}.flatMap { it.productos }.toSet()
    fun obtenerNumeroVecesProductoPedido(producto: Producto): Int = obtenerProductosPedidos().count { it == producto }
    fun agrupaClientesPorCiudad(): Map<Ciudad, List<Clientes>> = clientes.groupBy { it.ciudad }
    fun mapeaNombreCliente(): Map<String, Clientes> = clientes.associateBy { it.nombre }
    fun mapeaClienteCiudad(): Map<Clientes, Ciudad> = clientes.associateWith { it.ciudad }
    fun mapeaNombreClienteCiudad(): Map<String, Ciudad> =clientes.associate { Pair(it.nombre,it.ciudad) }
    fun obtenerClientesConMaxPedidos(): Clientes? = clientes.maxByOrNull { it.pedidos.size }


}
data class Clientes(val nombre: String, val ciudad: Ciudad, val pedidos: List<Pedido>) {
    override fun toString() = "$nombre from ${ciudad.nombre}"
    fun obtenerProductosPedidos(): List<Producto> = pedidos.flatMap {it.productos }
    fun encuentraProductoMasCaro(): Producto? = pedidos.filter{ it.estaEntregado }.flatMap { it.productos }.maxByOrNull { it.precio }
    fun obtenerProductoMasCaroPedido(): Producto? = pedidos.flatMap { it.productos }.maxByOrNull { it.precio }
    fun dineroGastado(): Double = pedidos.flatMap { it.productos }.sumOf { it.precio }
}

data class Pedido(val productos: List<Producto>, val estaEntregado: Boolean)

data class Producto(val nombre: String, val precio: Double) {
    override fun toString() = "'$nombre' for $precio"

}

data class Ciudad(val nombre: String) {
    override fun toString() = nombre

}

fun main() {
    val cadiz = Ciudad("Cádiz")
    val jerez = Ciudad("Jerez")
    val puertoReal = Ciudad("Puerto Real")
    val chiclana = Ciudad("Chiclana")
    val algeciras = Ciudad("Algeciras")

    val silla = Producto("Silla", 40.0)
    val mesa = Producto("Mesa", 80.50)
    val lampara = Producto("Lámpara", 30.25)
    val tv = Producto("Televisión", 200.99)
    val florero = Producto("Florero", 15.00)
    val teclado = Producto("Teclado", 31.00)

    val pedido1 = Pedido(listOf(florero, florero, lampara), false)
    val pedido2 = Pedido(listOf(mesa, tv, silla, silla, silla, silla), false)
    val pedido3 = Pedido(listOf(teclado, tv), true)
    val pedido4 = Pedido(listOf(teclado, tv, florero, silla, mesa), true)
    val pedido5 = Pedido(listOf(florero, mesa), true)
    val pedido6 = Pedido(listOf(teclado, lampara), false)
    val pedido7 = Pedido(listOf(teclado),true)
    val pedido8 = Pedido(listOf(mesa),false)
    val pedido9 = Pedido(listOf(),true)
    val pedido10 = Pedido(listOf(),false)

    val cliente1 = Clientes("Alejandro", chiclana, listOf(pedido1, pedido4, pedido5))
    val cliente2 = Clientes("Bruno", algeciras, listOf(pedido1, pedido2, pedido4))
    val cliente3 = Clientes("María", puertoReal, listOf(pedido5, pedido6))
    val cliente4 = Clientes("Fernando", jerez, listOf(pedido3, pedido6))
    val cliente5 = Clientes("Sergio", algeciras, listOf(pedido4, pedido3))
    val cliente6 = Clientes("Hugo", chiclana, listOf(pedido5))
    val cliente7 = Clientes("Prueba",jerez, listOf())
    val cliente8 = Clientes("Alberto",puertoReal, listOf(pedido8,pedido7))
    val cliente9 = Clientes("Pedro",cadiz, listOf(pedido9,pedido10))

    val tienda1 = Tienda("Tienda Pakito", listOf(cliente1,cliente2,cliente3,cliente4,cliente5,cliente6,cliente7,cliente8,cliente9))

    println("- TEST CLASE CLIENTE -\n\n")

    println("CLIENTE 6")
    println("Productos pedidos (debe salir mesa y florero):")
    println(cliente6.obtenerProductosPedidos())
    println("Producto más caro y entregado pedido (debe salir mesa):")
    println(cliente6.encuentraProductoMasCaro())
    println("Producto más caro pedido (debe salir mesa):")
    println(cliente6.obtenerProductoMasCaroPedido())
    println("Dinero gastado (debe salir 95.5):")
    println(cliente6.dineroGastado())

    println("\n\n")

    println("CLIENTE 8")
    println("Productos pedidos (mesa y teclado):")
    println(cliente8.obtenerProductosPedidos())
    println("Producto más caro y entregado pedido (teclado):")
    println(cliente8.encuentraProductoMasCaro())
    println("Producto más caro pedido (mesa):")
    println(cliente8.obtenerProductoMasCaroPedido())
    println("Dinero gastado (111.50):")
    println(cliente8.dineroGastado())

    println("\n\n")

    println("CLIENTE 7")
    println("Productos pedidos (lista vacía):")
    println(cliente7.obtenerProductosPedidos())
    println("Producto más caro y entregado pedido (null):")
    println(cliente7.encuentraProductoMasCaro())
    println("Producto más caro pedido (null):")
    println(cliente7.obtenerProductoMasCaroPedido())
    println("Dinero gastado (0.0):")
    println(cliente7.dineroGastado())

    println("\n\n")

    println("CLIENTE 9")
    println("Productos pedidos (lista vacía):")
    println(cliente9.obtenerProductosPedidos())
    println("Producto más caro y entregado pedido (null):")
    println(cliente9.encuentraProductoMasCaro())
    println("Producto más caro pedido (null):")
    println(cliente9.obtenerProductoMasCaroPedido())
    println("Dinero gastado (0.0):")
    println(cliente9.dineroGastado())

    println("\n\n - TEST CLASE TIENDA - \n\n")
}