package com.example.techcorp3.model;

import java.util.List;

public class ImportSummary {
    private Integer importedCount;
    private List<String> errors;

    public ImportSummary(Integer importedCount, List<String> errors) {
        this.importedCount = importedCount;
        this.errors = errors;
    }

    public Integer getImportedCount() {
        return importedCount;
    }

    public void setImportedCount(Integer importedCount) {
        this.importedCount = importedCount;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ImportSummary{" +
                "importedCount=" + importedCount +
                ", errors=" + errors +
                '}';
    }
}
