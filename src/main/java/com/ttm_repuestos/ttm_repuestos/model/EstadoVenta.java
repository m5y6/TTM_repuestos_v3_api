package com.ttm_repuestos.ttm_repuestos.model;

/**
 * Representa los posibles estados de una venta o pedido.
 */
public enum EstadoVenta {
    /**
     * El pedido ha sido creado por el cliente pero aún no ha sido revisado por un vendedor.
     */
    PENDIENTE,

    /**
     * Un vendedor ha aceptado el pedido y está preparando los productos.
     */
    EN_PROCESO,

    /**
     * El pedido ha sido completado y despachado/entregado.
     */
    COMPLETADO,

    /**
     * El pedido ha sido cancelado.
     */
    CANCELADO
}
