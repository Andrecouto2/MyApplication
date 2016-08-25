package com.esperienza.intranetmall.mobile.util;

    import java.util.InputMismatchException;

public class ValidaCPF {
    public static boolean ValidaCPF(String vrCPF)
    {
        String valor = vrCPF.replace(".", "");
        valor = valor.replace("-", "");
        valor = valor.replace(" ","");


        if (valor.length() != 11)
            return false;

        boolean igual = true;
        for (int i = 1; i < 11 && igual; i++)
            if (valor.charAt(i) != valor.charAt(0))
                igual = false;

        if (igual || valor == "12345678909")
            return false;

        int[] numeros = new int[11];
        for (int i = 0; i < 11; i++)
            numeros[i] = Integer.parseInt(
                    String.valueOf(valor.charAt(i)));

        int soma = 0;
        for (int i = 0; i < 9; i++)
            soma += (10 - i) * numeros[i];

        int resultado = soma % 11;
        if (resultado == 1 || resultado == 0)
        {
            if (numeros[9] != 0)
                return false;
        }
        else if (numeros[9] != 11 - resultado)
            return false;

        soma = 0;
        for (int i = 0; i < 10; i++)
            soma += (11 - i) * numeros[i];

        resultado = soma % 11;

        if (resultado == 1 || resultado == 0)
        {
            if (numeros[10] != 0)
                return false;

        }
        else
        if (numeros[10] != 11 - resultado)
            return false;
        return true;

    }
    public static String imprimeCPF(String CPF) {
        return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." + CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }



}
