package systems.memetic.memetiq.domain;

import systems.memetic.memetiq.tables.records.MediaRecord;

import java.net.MalformedURLException;
import java.net.URL;

public record Media(
    String name,
    URL url
) {
    public Media(MediaRecord record) throws MalformedURLException {
        this(record.getName(), new URL(record.getUrl()));
    }
}
