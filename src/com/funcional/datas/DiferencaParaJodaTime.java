package com.funcional.datas;

public class DiferencaParaJodaTime {
    public static void main(String[] args) {
        /** É importante lembrar que a nova API de datas (JSR-310) é baseada no Joda-Time,
         mas que não é uma cópia. Existem algumas diferenças de design que foram cuidadosamente
         apontadas pelo Stephen Colebourne em seu blog, no artigoWhy JSR-310
         isn’t Joda-Time:
         http://blog.joda.org/2009/11/why-jsr-310-isn-joda-time_4941.html.
         As principais mudanças foram inspiradas pelas falhas de design do Joda-Time,
         como é o caso das referências nulas. No Joda-Time não só era possível fornecer
         valores nulos para grande maioria de seus métodos, como isso tinha um significado
         pra cada modelo. Por exemplo, para as representações de data e tempo o valor null
         significava 1970-01-01T00:00Z. Para as classes Duration e Period, null
         significava zero.
         Essa é uma estratégia de design um tanto inesperada, e que pode facilmente causar
         bugs em nossas aplicações.
         Há ainda o caso da classe DateTime que implementa ReadableInstant,
         sendo que como uma interpretação humana de tempo não deveria. No fim, ela acaba
         sendo uma projeção de uma linha do tempo de máquina em uma linha do tempo
         humana, ficando limitada à precisão de milissegundos.
        */

    }
}
