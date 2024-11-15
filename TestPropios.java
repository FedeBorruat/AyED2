package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import aed.CustomComparator;
import aed.Ciudad;
import aed.Heap;

public class TestPropios {

    int cantCiudades;
    Traslado[] listaTraslados;
    ArrayList<Integer> actual;

    @BeforeEach
    void init(){}
    
    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        assertEquals(s1.size(), s2.size());
        for (int e1 : s1) {
            boolean encontrado = false;
            for (int e2 : s2) {
                if (e1 == e2) encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " +  e1 + " en el arreglo " + s2.toString());
        }
    }

    @Test
    //Test del BestEffort   
    void despachar_mas_redituable_sin_traslados(){
        Traslado[] unTraslado = new Traslado[]{new Traslado(0, 0, 1, 1000, 1)};
            BestEffort sis = new BestEffort(2, unTraslado);
            sis.despacharMasRedituables(1);
            // ya no hay traslados para despachar
            assertEquals(0, sis.despacharMasAntiguos(1).length);
            assertEquals(0, sis.despacharMasAntiguos(100).length);
        }

    @Test   
        void despachar_mas_antiguo_sin_traslados(){
        Traslado[] unTraslado = new Traslado[]{new Traslado(0, 0, 1, 1000, 1)};
            BestEffort sis = new BestEffort(2, unTraslado);
            sis.despacharMasAntiguos(1);
            // ya no hay traslados para despachar
            assertEquals(0,sis.despacharMasAntiguos(1).length);
            assertEquals(0,sis.despacharMasAntiguos(100).length); 
        }
    
    @Test
        void ganancia_perdida_superavit_sin_despachar(){
        Traslado [] listaTraslados = new Traslado[]{
                                            new Traslado(1, 0, 1, 100, 10),
                                            new Traslado(2, 0, 1, 400, 20),
                                            new Traslado(3, 3, 4, 500, 50),
                                            new Traslado(4, 4, 3, 500, 11),
                                            new Traslado(5, 1, 0, 1000, 40),
                                            new Traslado(6, 1, 0, 1000, 41),
                                            new Traslado(7, 6, 3, 2000, 42),
                                            new Traslado(8, 3, 2, 20, 43),
                                            new Traslado(9, 5, 4, 200, 44),
                                            new Traslado(10, 3, 6, 200, 12),
                                            new Traslado(11, 5, 6, 200, 2),
                                            new Traslado(12, 2, 1, 20, 1),
                                        };
        BestEffort sis = new BestEffort(7, listaTraslados);
        //al no haber echo ningun despacho, todos tienen ganancia y perdida 0
            assertSetEquals(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6)),sis.ciudadesConMayorGanancia());
            assertSetEquals(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6)),sis.ciudadesConMayorPerdida());
            assertEquals(0 ,sis.ciudadConMayorSuperavit());
        }

    @Test
        void registrar_traslados_vacios() {
            Traslado [] listaTraslados = new Traslado[]{
                new Traslado(1, 0, 1, 100, 10),
                new Traslado(2, 0, 1, 400, 20),
                new Traslado(3, 3, 4, 500, 50),
                new Traslado(4, 4, 3, 500, 11),
                new Traslado(5, 1, 0, 1000, 40),
                new Traslado(6, 1, 0, 1000, 41),
                new Traslado(7, 6, 3, 2000, 42),
                new Traslado(8, 3, 2, 20, 43),
                new Traslado(9, 5, 4, 200, 44),
                new Traslado(10, 3, 6, 200, 12),
                new Traslado(11, 5, 6, 200, 2),
                new Traslado(12, 2, 1, 20, 1),
            };
            BestEffort sis      = new BestEffort(7, listaTraslados);
            BestEffort nuevosis = new BestEffort(7 , listaTraslados);
            Traslado[] vacio = new Traslado[]{};
            nuevosis.registrarTraslados(vacio);
            //deberian tener todas las estadisticas iguales 
            assertSetEquals(sis.ciudadesConMayorGanancia(),nuevosis.ciudadesConMayorGanancia());
            assertSetEquals(sis.ciudadesConMayorPerdida(),nuevosis.ciudadesConMayorPerdida());
            assertEquals(sis.ciudadConMayorSuperavit(),nuevosis.ciudadConMayorSuperavit());
            assertEquals(Arrays.equals(sis.despacharMasAntiguos(1), nuevosis.despacharMasAntiguos(1)), true);
        }
    @Test
    //Test del heap y comparadores
        void ver_raiz(){
            ArrayList<Ciudad> ListaCiudades = new ArrayList<Ciudad>();
            Ciudad a = new Ciudad(0);
            Ciudad b = new Ciudad(1);
            Ciudad c = new Ciudad(2);
            Traslado t1 = new Traslado(1, 0, 1, 100, 10);
            Traslado t2 = new Traslado(2, 0, 1, 500, 20);
            Traslado t3 = new Traslado(3, 2, 4, 500, 5);
            a.actualizar(0, t1);
            b.actualizar(1, t2); // verificamos que actualizar funciona correctamente.
            c.actualizar(0, t3);
            ListaCiudades.add(a);
            ListaCiudades.add(b);
            ListaCiudades.add(c);
            Heap heapbalance = new Heap<>(ListaCiudades, CustomComparator.BY_BALANCE);
            //la raiz deberia ser el de mayor balance, en caso de empate devuelve el de menor id
            assertEquals(heapbalance.getRaiz() ,c);

            ArrayList<Traslado> listaTraslados = new ArrayList<Traslado>();
            
            listaTraslados.add(t1);
            listaTraslados.add(t2);
            listaTraslados.add(t3);
            Heap heapganancia = new Heap<>(listaTraslados, CustomComparator.BY_GANANCIA);
            //la raiz deberia ser el de mayor ganancia neta, en caso de empate devuelve el de menor id
            assertEquals(heapganancia.getRaiz() ,t2);

            Heap heaptime = new Heap<>(listaTraslados, CustomComparator.BY_TIMESTAMP);
            listaTraslados.add(t1);
            listaTraslados.add(t2);
            listaTraslados.add(t3);
            //la raiz deberia ser el de menor timestamp, no puede empatar 
            assertEquals(heaptime.getRaiz() ,t3);
        }
    @Test
        void eliminar_raiz(){
            ArrayList<Traslado> listaTraslados = new ArrayList<Traslado>();
            listaTraslados.add(new Traslado(1, 0, 1, 100, 10));
            listaTraslados.add(new Traslado(2, 0, 1, 400, 20));
            listaTraslados.add(new Traslado(3, 3, 4, 500, 50));
            listaTraslados.add(new Traslado(4, 4, 3, 500, 11));
            listaTraslados.add(new Traslado(5, 1, 0, 1000, 40));
            listaTraslados.add(new Traslado(6, 1, 0, 1500, 41));
            listaTraslados.add(new Traslado(7, 6, 3, 2000, 42));
            listaTraslados.add(new Traslado(8, 3, 2, 20, 43));
            listaTraslados.add(new Traslado(9, 5, 4, 200, 44));
            listaTraslados.add(new Traslado(10, 3, 6, 200, 12));
            listaTraslados.add(new Traslado(11, 5, 6, 200, 2));
            listaTraslados.add(new Traslado(12, 2, 1, 20, 1));
            
            Heap heap = new Heap<>(listaTraslados, CustomComparator.BY_GANANCIA);
            int tamaño = heap.getSize();
            assertEquals(tamaño, 12);
            heap.eliminar();
            //deberia eliminar la raiz que era el trasaldo de 2000 y ahora la raiz deberia ser e traslado con gananacia 1500
            Traslado raiz = new Traslado(cantCiudades, cantCiudades, cantCiudades, cantCiudades, cantCiudades);
            raiz=(Traslado) heap.getRaiz();
            assertEquals(raiz.getGananciaNeta(),1500);
            tamaño=heap.getSize();
            assertEquals(tamaño, 11);
        }
    @Test
        void eliminar_con_id(){
            ArrayList<Traslado> listaTraslados = new ArrayList<Traslado>();
            listaTraslados.add(new Traslado(1, 0, 1, 100, 10));
            listaTraslados.add(new Traslado(2, 0, 1, 400, 20));
            listaTraslados.add(new Traslado(3, 3, 4, 500, 50));
            listaTraslados.add(new Traslado(4, 4, 3, 500, 11));
            listaTraslados.add(new Traslado(5, 1, 0, 1000, 40));
            listaTraslados.add(new Traslado(6, 1, 0, 1500, 41));
            listaTraslados.add(new Traslado(7, 6, 3, 2000, 42));
            listaTraslados.add(new Traslado(8, 3, 2, 20, 43));
            listaTraslados.add(new Traslado(9, 5, 4, 200, 44));
            listaTraslados.add(new Traslado(10, 3, 6, 200, 12));
            listaTraslados.add(new Traslado(11, 5, 6, 200, 2));
            listaTraslados.add(new Traslado(12, 2, 1, 20, 1));
            
            Heap heap = new Heap<>(listaTraslados, CustomComparator.BY_GANANCIA);
            int tamaño = heap.getSize();
            assertEquals(tamaño, 12);
            Traslado eliminar = new Traslado(7, 6, 3, 2000, 42);
            Traslado[] l = new Traslado[1];
            l[0]=eliminar;
            heap.eliminarConIds(l);
            //deberia eliminar la raiz que era el trasaldo pasado como parametro
            Traslado raiz = new Traslado(cantCiudades, cantCiudades, cantCiudades, cantCiudades, cantCiudades);
            raiz=(Traslado) heap.getRaiz();
            assertEquals(raiz.getGananciaNeta(),1500);
            tamaño=heap.getSize();
            assertEquals(tamaño, 11);
        }
    @Test
        void agregar_y_eliminar (){
            ArrayList<Traslado> listaTraslados = new ArrayList<Traslado>();
            ArrayList<Traslado> vacia = new ArrayList<Traslado>();
            Traslado t1 = new Traslado(1, 0, 1, 500, 10);
            Heap heap = new Heap<>(vacia, CustomComparator.BY_GANANCIA);
            assertEquals(heap.getSize(),0); //heap vacio
            listaTraslados.add(t1);
            heap.agregar(listaTraslados);
            assertEquals(heap.getSize(),1); // heap con t1
            assertEquals(heap.getRaiz(),t1);
            Traslado t2 = new Traslado(2, 0, 1, 400, 20);
            listaTraslados.clear();
            listaTraslados.add(t2);
            heap.agregar(listaTraslados);
            assertEquals(heap.getSize(),2); //heap con t1 y t2
            heap.eliminar(); //elimina t1 porque es la raiz
            assertEquals(heap.getSize(),1);
            assertEquals(heap.getRaiz(),t2); // t2 pasa a ser la raiz
        }
    
    @Test //chatgpt         
        void rendimiento_con_gran_cantidad_de_traslados() {
            int N = 100000; // Un número grande de traslados
            ArrayList<Traslado> listaTraslados = new ArrayList<>();
            
            for (int i = 0; i < N; i++) {
                listaTraslados.add(new Traslado(i, i % 10, (i + 1) % 10, i * 10, i * 5));
            }
            
            Heap heap = new Heap<>(listaTraslados, CustomComparator.BY_GANANCIA);
            
            // Verificamos que el heap no esté vacío y que el tamaño sea correcto
            assertEquals(N, heap.getSize());
            
            // Probamos el despachado de la raíz (la de mayor ganancia)
            Traslado raiz = (Traslado) heap.getRaiz();
            assertNotNull(raiz);
            
            // Eliminamos la raíz varias veces para asegurarnos de que el heap funcione con N traslados
            for (int i = 0; i < 1000; i++) {
                heap.eliminar();
            }
            
            // Comprobamos que el tamaño se ha reducido adecuadamente
            assertEquals(N - 1000, heap.getSize());
        }
        
    @Test //chatgpt
        void ciudades_sin_ganancia_ni_perdida() {
            Traslado[] listaTraslados = new Traslado[]{
                new Traslado(1, 0, 1, 100, 10),
                new Traslado(2, 0, 1, 100, 10),
                new Traslado(3, 3, 4, 100, 10),
                new Traslado(4, 4, 3, 100, 10),
                new Traslado(5, 1, 0, 100, 10)
            };
            BestEffort sis = new BestEffort(5, listaTraslados);
            
            // Dado que todos los traslados tienen la misma ganancia y pérdida, deberían estar todas las ciudades
            assertSetEquals(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4)), sis.ciudadesConMayorGanancia());
            assertSetEquals(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4)), sis.ciudadesConMayorPerdida());
        }
        
    @Test //chat
        void eliminar_traslado_especifico() {
    ArrayList<Traslado> listaTraslados = new ArrayList<Traslado>();
    listaTraslados.add(new Traslado(2, 0, 1, 400, 20));
    listaTraslados.add(new Traslado(3, 3, 4, 500, 50));
    listaTraslados.add(new Traslado(4, 4, 3, 500, 11));
    
    Traslado t1=new Traslado(5, 1, 0, 1000, 40);
    listaTraslados.add(t1);
    Traslado t2=new Traslado(1, 0, 1, 500, 10);
    listaTraslados.add(t2);

    Heap heap = new Heap<>(listaTraslados, CustomComparator.BY_GANANCIA);
    
    // Aseguramos que la raíz es el traslado con mayor ganancia
    assertEquals(t1, heap.getRaiz()); // Usamos getGanancia() en lugar de getGananciaNeta
    
    // Eliminamos un traslado con un ID específico
    Traslado[] eliminar = new Traslado[] { listaTraslados.get(0) }; // Traslado con ID 1
    heap.eliminarConIds(eliminar);
    
    // Verificamos que la raíz ahora sea el siguiente traslado con mayor ganancia
    assertEquals(t2, heap.getRaiz()); // Usamos getGanancia() en lugar de getGananciaNeta
    
    // El tamaño del heap debería haber disminuido
    assertEquals(4, heap.getSize());
}

        
    }