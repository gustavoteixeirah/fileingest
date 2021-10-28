package dev.gustavoteixeira.filetransformer.writer;

import dev.gustavoteixeira.filetransformer.domain.Person;
import org.springframework.batch.item.ItemWriter;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.List;

public class CustomItemWriter implements ItemWriter<Person>, Closeable {

    private final PrintWriter writer;

    public CustomItemWriter() {
        OutputStream out;
        try {
            out = new FileOutputStream("output.txt");
        } catch (FileNotFoundException e) {
            out = System.out;
        }
        this.writer = new PrintWriter(out);
    }

    @Override
    public void write(final List<? extends Person> items) throws Exception {
        for (Person item : items) {
            writer.println(item.toString());
        }
    }

    @PreDestroy
    @Override
    public void close() throws IOException {
        writer.close();
    }
}
