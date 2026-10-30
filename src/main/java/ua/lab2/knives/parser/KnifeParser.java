package ua.lab2.knives.parser;

import java.nio.file.Path;
import java.util.List;
import ua.lab2.knives.KnifeException;
import ua.lab2.knives.model.Knife;

/** Reads a Knife XML document into a collection of objects. */
public interface KnifeParser {

    List<Knife> parse(Path xmlFile) throws KnifeException;
}
