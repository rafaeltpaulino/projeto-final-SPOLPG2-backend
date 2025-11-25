package br.com.ifsp.backend.builder;

import br.com.ifsp.backend.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import lombok.*;


public class ReleaseBuilder {

    private Release release;

    private ReleaseBuilder() {
        this.release = new Release();
        this.release.setLabels(new ArrayList<>());
        this.release.setTracks(new ArrayList<>());
    }

    public static ReleaseBuilder create() {
        return new ReleaseBuilder();
    }

    public ReleaseBuilder withTitle(String title) {
        this.release.setTitle(title);
        return this;
    }

    public ReleaseBuilder ofMaster(MasterRelease master) {
        this.release.setMasterRelease(master);
        return this;
    }

    public ReleaseBuilder releasedIn(LocalDate date) {
        this.release.setReleaseDate(date);
        return this;
    }

    public ReleaseBuilder fromCountry(Country country) {
        this.release.setCountry(country);
        return this;
    }

    public ReleaseBuilder withFormat(String format) {
        this.release.setFormat(format);
        return this;
    }

    public ReleaseBuilder asMainRelease(boolean isMain) {
        this.release.setMain(isMain);
        return this;
    }

    public ReleaseBuilder addLabel(Label label, String catalogNumber, String role) {
        ReleaseLabel releaseLabel = new ReleaseLabel();
        releaseLabel.setRelease(this.release);
        releaseLabel.setLabel(label);
        releaseLabel.setCatalogNumber(catalogNumber);
        releaseLabel.setRole(role);

        ReleaseLabelId id = new ReleaseLabelId();
        id.setLabelId(label.getId());
        releaseLabel.setId(id);

        this.release.getLabels().add(releaseLabel);
        return this;
    }

    public Release build() {
        if (release.getTitle() == null) throw new IllegalStateException("Release deve ter um título");
        if (release.getMasterRelease() == null) throw new IllegalStateException("Release deve pertencer a uma Master");

        return this.release;
    }
}
