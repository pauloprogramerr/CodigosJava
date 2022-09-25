package com.funcional.files;

import java.time.*;
import java.util.Calendar;

public class NovaAPIDatas {
    public static void main(String[] args) {
        /** A java.time vem do Joda Time
         *
         * O Joda-Time é uma poderosa biblioteca open source bastante conhecida
         * e utilizada no mercado, que trabalha com tempo, datas e cronologia.
         * http://www.joda.org/joda-time/ e https://jcp.org/en/jsr/detail?id=310.
         *
         * Aplixar uma transformação em um Calendar é um processp muito verboso,
         * como por exemplo para criar uma data com um mês a partir da data atual
         */

        Calendar mesQueVem = Calendar.getInstance();
        mesQueVem.add(Calendar.MONTH, 1);

        /** Com a API podemos fazer a mesma operação de forma mais moderna
         * utilizando sua interface fluente
         */
        LocalDate mesQueVen = LocalDate.now().plusMonths(1);
        System.out.println(mesQueVen);
        /** Para subtrair um ano faríamos: */
        LocalDate anoPassado = LocalDate.now().minusYears(1);
        System.out.println(anoPassado);

        /** Um ponto importante é que a classe LocalDate represena uma data sem horário
         * nem timezone, por exemplo 25-01-2014. Se as informações de horário forem
         * importantes, usamos a classe LocalDateTime de forma bem parecida
         */

        LocalDateTime agora = LocalDateTime.now();
        System.out.println(agora);

        /** Há ainda o LocalTime que representa somente a hora: */
        LocalTime agoras = LocalTime.now();
        System.out.println(agoras);

        /** Outra forma de criar uma LocalDateTime com horário específico
         * é utilizando o método atTime da classe LocalDate
         */
        LocalDateTime hojeAoMeioDia = LocalDate.now().atTime(12, 0);
        System.out.println(hojeAoMeioDia);

        /** Assim como fizemos com esse método atTime, sempre podemos utilizar
         * os métodos at para combinar os doferentes modelos.
          */
        LocalTime agora1 = LocalTime.now();
        LocalDate hoje = LocalDate.now();
        LocalDateTime dataEhora = hoje.atTime(agora1);
        System.out.println(dataEhora);

        /** Também podemos a partir desse LocalDateTime, Chamar o método aZone
         * para construir um ZonedDateTime, que é o mpetodo utilizado par
         * representar uma data com hora e timezone.
         */
        ZonedDateTime dataComHoraETimeZone =
                dataEhora.atZone(ZoneId.of("America/São Paulo"));
        System.out.println(dataComHoraETimeZone);

        /** Para converter esses objetivos para outras medidas de tempo podemos
         * utilizar os métodos to, como é o caso do toLocalDateTime presente
         * na classe ZonedDateTime:
          */
        LocalDateTime semTimeZone = dataComHoraETimeZone.toLocalDateTime();
        System.out.println(semTimeZone);

        /** O mesmo pode ser feito com o método toLocalDate da classe LocalDateTime
         * entre diversos outro métodos para conversão.
         * Além disso, as classes dessa nova API contam com o método estático of
         * que é um factory method para construção de suas novas instâncias.
         */

        LocalDate date = LocalDate.of(2014, 12,25);
        LocalDateTime dateTime =
                LocalDateTime.of(2014, 12, 25, 10, 30);
        System.out.println(date);
        System.out.println(dateTime);

        /** De forma similar ao setters, os novos modelos imutáveis possuem os
         * métodos withs para faxilitar a inserção de sias informações, Para modificar
         * o ano de um LocalDate, por exemplo, poderiamos utilizar o método withYear
          */

        LocalDate dataDoPassado = LocalDate.now().withYear(1988);

        // Para recuperar estas informações podemos utilizar seus métodos gets
        System.out.println(dataDoPassado.getYear());

        /** Existe tmbém outros comportamentos essencias, como saber se
         * alguma medida de tempo acontece antes, depois ou ao mesmo tempo que
         * outra. Para  esses casos utilizamos os métodos is.
         */

        LocalDate hj = LocalDate.now();
        LocalDate amanha = LocalDate.now().plusDays(1);

        System.out.println(hj.isBefore(amanha));
        System.out.println(hj.isAfter(amanha));
        System.out.println(hj.isEqual(amanha));



    }
}
