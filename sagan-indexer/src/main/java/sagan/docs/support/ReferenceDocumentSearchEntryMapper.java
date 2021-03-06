package sagan.docs.support;

import sagan.projects.Project;
import sagan.projects.ProjectRelease;
import sagan.search.SearchEntry;
import sagan.search.SearchEntryMapper;

import java.util.Date;

import org.jsoup.nodes.Document;

class ReferenceDocumentSearchEntryMapper implements SearchEntryMapper<Document> {

    private final Project project;
    private final ProjectRelease version;

    public ReferenceDocumentSearchEntryMapper(Project project, ProjectRelease version) {
        this.project = project;
        this.version = version;
    }

    @Override
    public SearchEntry map(Document document) {
        SearchEntry entry = new SearchEntry();
        entry.setPublishAt(new Date(0L));
        String text = document.text();

        entry.setRawContent(text);
        entry.setSummary(text.substring(0, Math.min(500, text.length())));
        entry.setTitle(document.title());
        entry.setSubTitle(String.format("%s (%s Reference)", project.getName(), version.getVersion()));
        entry.setPath(document.baseUri());
        entry.setCurrent(version.isCurrent());
        entry.setProjectId(project.getId());
        entry.setVersion(version.getVersion());

        entry.addFacetPaths("Projects", "Projects/Reference", "Projects/" + project.getName(), "Projects/"
                + project.getName() + "/" + version.getVersion());

        return entry;
    }

}
