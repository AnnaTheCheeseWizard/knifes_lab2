# Лабораторна робота 2 — варіант 7: Холодна зброя (Knife)

XML + XSD, розбір SAX/DOM/StAX, сортування через `Comparator`, валідація за XSD,
XSL-перетворення (групування за типом), логування (`java.util.logging`), unit-тести (JUnit 5).

## Структура

| Шлях | Призначення |
|------|-------------|
| `data/knives.xml` | XML-документ (корінь `Knives`, елементи `Knife`) |
| `data/knives.xsd` | XSD: прості й комплексні типи, enum, межі значень, `xs:ID`, обов'язкові атрибути |
| `data/knives.xsl` | XSLT 1.0: розбиття списку на підсписки за `Type` |
| `ua.lab2.knives.model` | Java-класи (records + enum), що відповідають XSD |
| `ua.lab2.knives.parser` | `SaxKnifeParser`, `DomKnifeParser`, `StaxKnifeParser`, спільні `KnifeBuilder`, `KnifeEventCollector` |
| `ua.lab2.knives.sorting` | `KnifeComparators` |
| `ua.lab2.knives.validation` | `XmlValidator` (XSD) |
| `ua.lab2.knives.transform` | `XslTransformer` |
| `ua.lab2.knives.app` | `App` (точка входу), `LoggingConfig` |

## Запуск

```bash
mvn test                      # тести + звіт JaCoCo (target/site/jacoco/index.html)
mvn package
java -jar target/knives-1.0.0.jar [knives.xml] [knives.xsd] [knives.xsl] [output.xml]
```

Результат XSL-перетворення за замовчуванням: `output/knives-by-type.xml`; лог: `logs/knives.log`.

## Публікація у віддалений репозиторій

```bash
git remote add origin <URL вашого репозиторію>
git push -u origin main
```
