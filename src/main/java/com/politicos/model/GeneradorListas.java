package com.politicos.model;

import java.util.Objects;
/*
public abstract class GeneradorListas<T extends Comparable<T>> {
    protected static final int ID_BASE = 1000;
    protected double dineroInicial;
    protected double incrementoDinero;

    // Cambiar la firma para usar wildcard <?> en el Class
    public final <L extends ListaEnlazadaBase<T>> void generarListaConCrecimiento(
        int tamañoInicial, 
        double factorCrecimiento, 
        Class<L> tipoLista  // Usar L en lugar de wildcard directamente
    ) {
        int n = tamañoInicial;
        while (true) {
            try {
                L lista = tipoLista.getDeclaredConstructor().newInstance();
                generarElementos(lista, n);
                System.out.println("\n--- Lista Generada (" + tipoLista.getSimpleName() + ") ---");
                lista.imprimir();
                
                ejecutarEstrategiaOrdenamiento("Burbuja", new OrdenamientoBurbuja<>(), lista.clonarLista());

                n = (int) (n * factorCrecimiento);
                if (n <= 0 || n > 10) throw new OutOfMemoryError();
            } catch (OutOfMemoryError e) {
                System.err.println("¡Desbordamiento! Último tamaño válido: " + n / factorCrecimiento);
                break;
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                break;
            }
        }
    }

    protected abstract void generarElementos(ListaEnlazadaBase<T> lista, int n);
    
    private static <T extends Comparable<T>> void ejecutarEstrategiaOrdenamiento(
            String nombrePrueba, EstrategiaOrdenamiento<T> estrategia, ListaEnlazadaBase<T> lista)
    {
        System.out.println("\n--- Ordenando con " + nombrePrueba + " ---");
        try {
            Objects.requireNonNull(estrategia, "La estrategia no puede ser null para " + nombrePrueba);
            Objects.requireNonNull(lista, "La lista no puede ser null para " + nombrePrueba);
            // Clonar de nuevo por si acaso la referencia original se pasó por error
            ListaEnlazadaBase<T> listaParaOrdenar = lista; //.clonarLista(); // Clonar si 'lista' no es ya un clon

            long startTime = System.nanoTime(); // Medir tiempo (opcional)
            listaParaOrdenar.ordenar(estrategia);
            long endTime = System.nanoTime(); // Medir tiempo (opcional)

            System.out.print("Resultado: ");
            listaParaOrdenar.imprimir();
            // System.out.printf("Tiempo empleado: %.3f ms%n", (endTime - startTime) / 1_000_000.0); // Opcional

        } catch (UnsupportedOperationException usoEx) {
             System.err.println("ERROR: La estrategia '" + nombrePrueba + "' requiere una operación no soportada (probablemente Nodo.setDato()). " + usoEx.getMessage());
        } catch (IllegalStateException ise) {
             System.err.println("ERROR: No se puede ordenar con '" + nombrePrueba + "'. " + ise.getMessage());
        } catch (Exception e) {
            System.err.println("ERROR inesperado durante ordenación '" + nombrePrueba + "': " + e.getMessage());
            e.printStackTrace(); // Mostrar traza completa para errores inesperados
        }
    }
}
*/