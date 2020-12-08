package com.miracle.iam.common.filter;

import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;

import java.util.Map;

public class DroolsFiltre implements AgendaFilter {

    private String veresion;

    public DroolsFiltre(String version){
        this.veresion = version;
    }

    @Override
    public boolean accept(Match match) {
        Map metadataMap = match.getRule().getMetaData();
        String version = metadataMap.get("version").toString();
        return this.veresion.contains(version);
    }
}
