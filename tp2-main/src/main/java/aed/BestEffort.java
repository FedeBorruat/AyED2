package aed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BestEffort {
    private Heap<Ciudad> mayorSuperavit;
    private ArrayList<Ciudad> mayorGanancia; // Mayor ganancia: ciudades con mayor ganancia (es un arrayList porque puede haber empates)
    private ArrayList<Ciudad> mayorPerdida; // Mayor perdida: ciudades con mayor perdida (es un arrayList porque puede haber empates)
    private ArrayList<Integer> mayorGananciaIds; 
    private ArrayList<Integer> mayorPerdidaIds;
    private Ciudad[] ciudades;
    private Heap<Traslado> trasladosMasAntiguos;
    private Heap<Traslado> trasladosMasRedituables;
    private int[] gananciaTotal;

    public BestEffort(int cantCiudades, Traslado[] traslados) { // O(|C| + |T|)
        
        ciudades = new Ciudad[cantCiudades];
        
        
        for (int i = 0; i < cantCiudades; i++) { // O(|C|)
            ciudades[i] = new Ciudad(i); 
        }

        mayorGanancia = new ArrayList<>(cantCiudades);
        mayorPerdida = new ArrayList<>(cantCiudades);
        mayorGananciaIds = new ArrayList<>(cantCiudades);
        mayorPerdidaIds = new ArrayList<>(cantCiudades);

        
        for (Ciudad ciudad : ciudades) { // O(|C|)
            mayorGanancia.add(ciudad);
            mayorPerdida.add(ciudad);
            mayorGananciaIds.add(ciudad.getId());
            mayorPerdidaIds.add(ciudad.getId());
        }

        
        gananciaTotal = new int[]{0, 0}; 


        ArrayList<Traslado> trasladosGanancia = new ArrayList<Traslado>(Arrays.asList(traslados));
        ArrayList<Traslado> trasladosTimestamp = new ArrayList<Traslado>(Arrays.asList(traslados));
        ArrayList<Ciudad> ciudadesarr = new ArrayList<Ciudad>(Arrays.asList(ciudades));


        //Algoritmo de Floyd:
        mayorSuperavit = new Heap<>(ciudadesarr, CustomComparator.BY_BALANCE);  // O(|C|)
        trasladosMasRedituables = new Heap<>(trasladosGanancia, CustomComparator.BY_GANANCIA);  // O(|T|)
        trasladosMasAntiguos = new Heap<>(trasladosTimestamp, CustomComparator.BY_TIMESTAMP);  // O(|T|)
    }

    private void actualizarBE(Ciudad[] ciudades, Traslado[] traslados) { // O(n*log(|C|)), n viene de "eliminar"
        int j = 0;
        
        while (j < traslados.length) { // O(n)
            gananciaTotal[0] = gananciaTotal[0] + traslados[j].getGananciaNeta();
            gananciaTotal[1] += 1;

            
            ciudades[traslados[j].getDestino()].actualizar(1, traslados[j]); // 1 = destino
            ciudades[traslados[j].getOrigen()].actualizar(0, traslados[j]); //  0 = origen

            actualizarMayorPerdidaYGanancia(traslados[j]); //O(1)

            this.mayorSuperavit.actualizar(ciudades[traslados[j].getOrigen()]); // O(log|C|)
            this.mayorSuperavit.actualizar(ciudades[traslados[j].getDestino()]); // O(log|C|)
            j++;
        }
        
        
    }
    
    private void actualizarMayorPerdidaYGanancia(Traslado traslado){// O(1)
        if (mayorGanancia.get(0).getGanancia() < ciudades[traslado.getOrigen()].getGanancia()) {
            this.mayorGanancia = new ArrayList<>(Arrays.asList(ciudades[traslado.getOrigen()])); 
            this.mayorGananciaIds = new ArrayList<>(Arrays.asList(ciudades[traslado.getOrigen()].getId())); 

        } else if (mayorGanancia.get(0).getGanancia() == ciudades[traslado.getOrigen()].getGanancia()) { // si tiene la misma ganancia lo agregamos a la lista.
            if (mayorGanancia.get(0).getId() != ciudades[traslado.getOrigen()].getId()){ 
                mayorGananciaIds.add(ciudades[traslado.getOrigen()].getId());
                mayorGanancia.add(ciudades[traslado.getOrigen()]);
            } else { // Si es la misma ciudad quien esta en la lista creamos una nueva lista con esa nueva ciudad.
                this.mayorGanancia = new ArrayList<>(Arrays.asList(ciudades[traslado.getOrigen()])); 
                this.mayorGananciaIds = new ArrayList<>(Arrays.asList(ciudades[traslado.getOrigen()].getId())); 
            }
        }

        // Aca hacemos lo mismo que antes pero con perdida.
        if (mayorPerdida.get(0).getPerdida() < ciudades[traslado.getDestino()].getPerdida()) {
            this.mayorPerdida = new ArrayList<>(Arrays.asList(ciudades[traslado.getDestino()])); 
            this.mayorPerdidaIds = new ArrayList<>(Arrays.asList(ciudades[traslado.getDestino()].getId())); 

        } else if (mayorPerdida.get(0).getPerdida() == ciudades[traslado.getDestino()].getPerdida()) {
            if (mayorPerdida.get(0).getId() != ciudades[traslado.getDestino()].getId()){
                mayorPerdidaIds.add(ciudades[traslado.getDestino()].getId());
                mayorPerdida.add(ciudades[traslado.getDestino()]);
            } else {
                this.mayorPerdida = new ArrayList<>(Arrays.asList(ciudades[traslado.getDestino()])); 
                this.mayorPerdidaIds = new ArrayList<>(Arrays.asList(ciudades[traslado.getDestino()].getId())); 
            }
        }    
    }

    public void registrarTraslados(Traslado[] traslados){ // O(log(|T|))
        ArrayList<Traslado> trasladosarr = new ArrayList<Traslado>(Arrays.asList(traslados));

        this.trasladosMasAntiguos.agregar(trasladosarr); // O(log|T|)
        this.trasladosMasRedituables.agregar(trasladosarr); // O(log|T|)
    }

    public int[] despacharMasRedituables(int n) {// O(n*(log(|T|)+log(|C|)))
        int i = 0;
        
        int[] res;
        Traslado[] listaDeTraslados;

        if (n > trasladosMasRedituables.getSize()) {  
            int tamHeap = this.trasladosMasRedituables.getSize(); 
            res =  new int[tamHeap];
            listaDeTraslados =  new Traslado[tamHeap];
            while (i < trasladosMasRedituables.getSize()) { //O(n*log(|T|))
                
                res[i] = trasladosMasRedituables.getRaiz().getPosicionTimestamp();  
                listaDeTraslados[i] = trasladosMasRedituables.getRaiz();
                trasladosMasRedituables.eliminar();// O(log(|T|))
                i++;
            }
        } else {
            res =  new int[n];
            listaDeTraslados =  new Traslado[n];
            while (i < n) { // O(n)
                
                res[i] = trasladosMasRedituables.getRaiz().getPosicionTimestamp();  
                listaDeTraslados[i] = trasladosMasRedituables.getRaiz();
                trasladosMasRedituables.eliminar();
                i++;
            }
        }
        actualizarBE(ciudades, listaDeTraslados); //O(n*log(|C|))

        this.trasladosMasAntiguos.eliminarConIds(listaDeTraslados);
        return res;                 
    }

    public int[] despacharMasAntiguos(int n){ //  O(n*(log(|T|)+log(|C|))) igual que "despacharMasRedituables"
        int i = 0;
        int[] res;
        Traslado[] listaDeTraslados;
        
        if (n > trasladosMasAntiguos.getSize()) { 
            int tamHeap = this.trasladosMasAntiguos.getSize(); 
            res =  new int[tamHeap];
            listaDeTraslados =  new Traslado[tamHeap]; 
            while (i < trasladosMasAntiguos.getSize()) {
                
                res[i] = trasladosMasAntiguos.getRaiz().getPosicionGanancia();  
                listaDeTraslados[i] = trasladosMasAntiguos.getRaiz();
                trasladosMasAntiguos.eliminar();
                i++;
            }
        } else {
            res =  new int[n];
            listaDeTraslados =  new Traslado[n]; 
            while (i < n) {
                
                res[i] = trasladosMasAntiguos.getRaiz().getPosicionGanancia();  
                listaDeTraslados[i] = trasladosMasAntiguos.getRaiz();
                trasladosMasAntiguos.eliminar();
                i++;
            }
        }
        actualizarBE(ciudades, listaDeTraslados);

        this.trasladosMasRedituables.eliminarConIds(listaDeTraslados);
        return res;                 
    }

    public int ciudadConMayorSuperavit(){ // O(1)
        return this.mayorSuperavit.getRaiz().getId();
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){// O(1)
        
        return this.mayorGananciaIds;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){// O(1)
        
        return this.mayorPerdidaIds;
    }

    public int gananciaPromedioPorTraslado(){// O(1)
        
        return (int) (this.gananciaTotal[0] / gananciaTotal[1]);
    }
    
}
