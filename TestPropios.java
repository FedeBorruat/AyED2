package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

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
            equals(sis.despacharMasAntiguos(1).toString().compareTo(nuevosis.despacharMasAntiguos(1).toString())==0);
        }
    }   