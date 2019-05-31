package org.isel.mpd;

import htmlflow.HtmlView;
import htmlflow.StaticHtml;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.vertx.core.http.HttpServerResponse;
import org.isel.mpd.weather.WeatherInfo;
import org.xmlet.htmlapifaster.Body;
import org.xmlet.htmlapifaster.Html;
import org.xmlet.htmlapifaster.Table;
import org.xmlet.htmlapifaster.Tbody;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class WeatherPastView implements View<Observable<WeatherInfo>> {
    @Override
    public void write(HttpServerResponse resp) {
        throw new UnsupportedOperationException("This view requires a Model. You should invoke write(resp, model) instead!");
    }

    @Override
    public void write(HttpServerResponse resp, Observable<WeatherInfo> model) {
        resp.setChunked(true); // response.putHeader("Transfer-Encoding", "chunked");
        resp.putHeader("content-type", "text/html");
        model.subscribeWith(new Observer<WeatherInfo>() {
            Tbody<Table<Body<Html<HtmlView>>>> tbody;
            public void onSubscribe(Disposable d) { tbody = writeHeader(resp); }
            public void onNext(WeatherInfo info) { writeTableRow(tbody, info); }
            public void onError(Throwable e) { /* TO DO !!! */ }
            public void onComplete() {
                // Close remaining elements
                tbody
                    .__() // tbody
                    .__() // table
                    .__() // body
                    .__();// html
                resp.end();
            }
        });
    }

    private static Tbody<Table<Body<Html<HtmlView>>>> writeHeader(HttpServerResponse resp) {
        return StaticHtml.view(new ResponsePrintStream(resp))
            .html()
                .head()
                    .title()
                        .text("PastWeathet")
                    .__()// title
                .__()// head
                .body()
                    .h1()
                        .text("Historical Weather Data")
                    .__()// h1
                    .table()
                        .thead()
                            .tr()
                                .th().text("Date").__()
                                .th().text("Description").__()
                                .th().text("Temp in Celsius").__()
                            .__()
                        .__()
                        .tbody();
    }

    private static void writeTableRow(Tbody<Table<Body<Html<HtmlView>>>> tbody, WeatherInfo info) {
        tbody
            .tr()
                .td()
                    .text(info.getDate())
                .__() // td
                .td()
                    .text(info.getDesc())
                .__() // td
                .td()
                    .text(info.getTempC())
                .__(); // td
    }

    private static class ResponsePrintStream extends PrintStream {
        /**
         * We may improve this with a Buffer.
         * For now we just want to see the effect of sending
         * char by char to the browser !!!
         */
        public ResponsePrintStream(HttpServerResponse resp) {
            super(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    char c = (char) b;
                    resp.write(String.valueOf(c));
                }
            });
        }
    }
}
