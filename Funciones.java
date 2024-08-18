package aed;

class Funciones {
    int cuadrado(int x) {
        int res;
        res=x*x;
        return(res);
    }

    double distancia(double x, double y) {
        double res;
        res= Math.sqrt((y*y)+(x*x));
        return res;
    }

    boolean esPar(int n) {
        int resu;
        resu = (n%2);
        return (resu==0);
    }

    boolean esBisiesto(int n) {
        boolean cond1;
        boolean cond2;
        boolean cond3;
        cond1 = (n%4==0);
        cond2 = (n%100!=0);
        cond3 = (n%400==0);
        return (cond3==true)||((cond1==true)&&(cond2==true));
    }

    int factorialIterativo(int n) { 
        int resu;
        resu=1;
        if (n==0) {
        return resu;            
        }
        else
        while ((n-1)!=0) {
        resu=n*resu;
        n=n-1;            
        }
        return resu;
    }

    int factorialRecursivo(int n) {
        int resu;
        if ((n==0)||(n==1)){
        resu=1;
        return resu;
        }
        else
        resu=n*factorialIterativo(n-1);
        return resu;
    }

    boolean esPrimo(int n) {
        int sumDiv;
        int div;
        div=n;
        sumDiv=0;
        while (div!=0)
        {
        if (n%div==0) 
        {sumDiv=sumDiv+div;}
        div=div-1;
        }
        return (sumDiv==(n+1));
    }

    int sumatoria(int[] numeros) {
        int resu;
        resu=0;
        for (int i = 0; i < numeros.length; i++) {
        resu = resu + numeros[i];
        }
        return resu;
    }

    int busqueda(int[] numeros, int buscado) {
    int resu;
    resu=0;
    for (int i = 0; i < numeros.length; i++) {
    if (buscado==numeros[i]) {
    resu=i;}
    }
    return resu;
    }

    boolean tienePrimo(int[] numeros) {
    boolean resu;
    resu=false;
    for (int i = 0; i < numeros.length; i++) {
    if ((esPrimo((numeros[i]))==true)) {
    resu=true;
    }
    }
    return resu;
    }

    boolean todosPares(int[] numeros) {
    boolean resu;
    resu=true;
    for (int i = 0; i < numeros.length; i++) {
    if (numeros[i]%2!=0) {
    resu=false;
    }
    }
    return resu;
    }

    boolean esPrefijo(String s1, String s2) {
    boolean resu;
    resu=true;
    if (s2.length()<s1.length()) {
    resu=false;        
    }
    for (int i = 0; (i < s1.length())&&(i<s2.length()); i++) {
    if (s1.charAt(i)!=(s2.charAt(i))) {
    resu=false;
    return resu;
    }
    }
    return resu;
    }

    boolean esSufijo(String s1, String s2) {
    return (esPrefijo(s1,s2));
    }
}
