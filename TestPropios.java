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
            a.sumarBalance(50);
            b.sumarBalance(20);
            c.sumarBalance(50);
            ListaCiudades.add(a);
            ListaCiudades.add(b);
            ListaCiudades.add(c);
            Heap heapbalance = new Heap<>(ListaCiudades, CustomComparator.BY_BALANCE);
            //la raiz deberia ser el de mayor balance, en caso de empate devuelve el de menor id
            assertEquals(heapbalance.getRaiz() ,a);
     
            ArrayList<Traslado> listaTraslados = new ArrayList<Traslado>();
            Traslado aa = new Traslado(1, 0, 1, 100, 10);
            Traslado bb = new Traslado(2, 0, 1, 500, 20);
            Traslado cc = new Traslado(3, 3, 4, 500, 5);
            listaTraslados.add(aa);
            listaTraslados.add(bb);
            listaTraslados.add(cc);
            Heap heapganancia = new Heap<>(listaTraslados, CustomComparator.BY_GANANCIA);
            //la raiz deberia ser el de mayor ganancia neta, en caso de empate devuelve el de menor id
            assertEquals(heapganancia.getRaiz() ,bb);

            Heap heaptime = new Heap<>(listaTraslados, CustomComparator.BY_TIMESTAMP);
            listaTraslados.add(aa);
            listaTraslados.add(bb);
            listaTraslados.add(cc);
            //la raiz deberia ser el de menor timestamp, no puede empatar 
            assertEquals(heaptime.getRaiz() ,cc);
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
            assertEquals(raiz.gananciaNeta,1500);
        }

        void eliminar_elemento_del_medio(){}
        void eliminar_ultimo_elemento_agregado (){}


    }