package org.isel.mpd.weather.restdto;

public class SearchApiDto {
    private final SearchApiResultDto[] result;

    public SearchApiDto(SearchApiResultDto[] result) {
        this.result = result;
    }

    public SearchApiResultDto[] getResult() {
        return result;
    }
}
