package com.funcional.datas;

import java.io.StringReader;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class EnumsNoLugarDeConstantes {
    public static void main(String[] args) {


        /** Essa API fornece o uso de Enums no lugar das famosas constantes
         * do Calendar. Trabalhar com enums é uma boa prática, afinal seu código fica maus legível
         * nos dois casos o valor da saída é 25/12/2014
         */
        System.out.println(LocalDate.of(2014, 12,25));
        System.out.println(LocalDate.of(2014, Month.DECEMBER, 25));
        /**
         * Outra vantegem de usar enums são seus diversos métodos auxiliares
         * Note como é simples consultar  primeiro dia do trimestre de determindo mês
         * ou então incrementar/decrementar meses.
         */

        System.out.println(Month.DECEMBER.firstMonthOfQuarter());
        System.out.println(Month.DECEMBER.plus(2));
        System.out.println(Month.DECEMBER.minus(1));

        /** Para imprimir o nome de um mês formatado, podemos utilizar o método
         * getDisplayName fornecendo o estilo de formatação(completo, resumido, entre outros)
         * e também o Locale
          */

        Locale pt = new Locale("pt");
        System.out.println(Month.DECEMBER
                .getDisplayName(TextStyle.FULL, pt));

        System.out.println(Month.DECEMBER
                .getDisplayName(TextStyle.SHORT, pt));

        /** Formatando com a nova API de datas
         *
         * A formatação d datas também recebeu melhorias. Formatar um LocalDateTime
         * por exemplo, é um processo bem simples! tudo que você precisa fazer é chamar o método
         * format passando um DateTimeFormatter com parâmetro
         * */

        LocalDateTime agora = LocalDateTime.now();
        String resultado =  agora.format(DateTimeFormatter.ISO_LOCAL_TIME);
        System.out.println(resultado);

        /** Para criar um DateTimeFormatter com um novo padrão, uma das formas
         * é usar o método ofPattern, que reccebe uma String como parâmetro
         */
        LocalDateTime agora1 = LocalDateTime.now();
        agora1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        /** Podemos transformar uma String rm alguma representação de data ou tempo válida
         * e para isso utilizamos o métpdp parse! Podemos retornar o nosso resultado
         * anterior em um LocalDate utilizando o mesmo formatador.
         * Não podemos retornar um LocalDateTime, afinal quando formatamos
         * em data (com a padrão dd/MM/yyyy), perdemos as informações de tempo que
         * resultaria em uma DateTimeParseException
         */

        LocalDateTime agora2 = LocalDateTime.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String result = agora2.format(formatador);
        LocalDate agoraEmData = LocalDate.parse(result, formatador);
        System.out.println(agoraEmData);

        /** Calcular a diferencça entre dois Calendars, depomos fazer esta
         * operação de forma mais simples utilizando o enum ChronoUnit da nova API
         */

        LocalDate ag = LocalDate.now();
        LocalDate outraData = LocalDate.of(1989, Month.JANUARY, 25);
        long dias = ChronoUnit.DAYS.between(outraData, ag);
        System.out.println(dias);

        // diferença de anos e meses entre duas datas
        long d = ChronoUnit.DAYS.between(outraData, ag);
        long m = ChronoUnit.MONTHS.between(outraData, ag);
        long a = ChronoUnit.YEARS.between(outraData, ag);

        System.out.printf("%s dias %s meses %s anos", d, m, a);

        /** Outra forma de conseguir o resultado que esperamos: os dias,
         * meses e anos entre duas datas, é utilizando o modelo Period.
         * Essa API também possui o método between, que recebe duas instâncias de Locale
         */

        LocalDate ago = LocalDate.now();
        LocalDate outraDat = LocalDate.of(2023, Month.JANUARY, 25);
        Period periodo = Period.between(outraDat, ago);
        System.out.printf("\n %s dias %s meses %s ano", periodo.getDays(),
                periodo.getMonths(), periodo.getYears());

        /** Duration considera a medida de tempo (horas, minutos, segundos)
         *
         */
        LocalDateTime ags = LocalDateTime.now();
        LocalDateTime daquiUmaHora = LocalDateTime.now().plusHours(1);
        Duration duration = Duration.between(ags, daquiUmaHora);

        if (duration.isNegative()) duration = duration.negated();

        System.out.printf("%s horas, %s minutos %s segundos",
                duration.toHours(), duration.toMinutes(), duration.toSeconds());






















    }
}
