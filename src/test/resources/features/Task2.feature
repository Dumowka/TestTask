Feature: Task2
  Scenario: Task2
    Given Сайт www.rts-tender.ru
    Then Нажатие в футере в разделе поставщиков на ссылку 223_ФЗ
    Then Нажатие на расширенный поиск
    Then Получение всех номеров доступных закупок со страницы и проверить, что закупка каждая закупка встречается только раз