package org.acme.conference.session;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class SessionStore {

    @Inject
    private SessionRepository repository;

    @ConfigProperty(name = "features.session-integration", defaultValue = "false")
    boolean sessionIntegration;

    @RestClient
    SpeakerService speakerService;

    public SessionStore() {
    }

    public Collection<Session> findAll () {
        return repository.findAll().list();
    }

    @Transactional
    public Session save (Session session) {
        repository.persist(session);
        return session;
    }

    @Transactional
    public Optional<Session> updateById (String sessionId, Session session) {
        Optional<Session> sessionOld = findById(sessionId);
        if (!sessionOld.isPresent()) {
            return Optional.empty();
        }

        sessionOld.ifPresent(s -> {
            s.schedule = session.schedule;
            s.speakers.clear();
            s.speakers.addAll(session.speakers);
            repository.persist(s);
        });
        return Optional.ofNullable(session);
    }

    public Optional<Session> findById (String sessionId) {
        Optional<Session> result = repository.find("id", sessionId)
                .stream()
                .findFirst();
        if (sessionIntegration && result.isPresent()) {
            List<SpeakerFromService> speakers = speakerService.listAll();
            Session session = result.get();
            for (var speaker : session.speakers) {
                var serviceSpeaker = speakers.stream().filter(s -> s.uuid.equals(speaker.uuid)).findFirst();
                if (serviceSpeaker.isPresent()) {
                    var sameSpeaker = serviceSpeaker.get();
                    speaker.name = sameSpeaker.nameFirst + " " + sameSpeaker.nameLast;
                }
            }
            return Optional.of(session);
        }
        return result;
    }

    @Transactional
    public Optional<Session> deleteById (String sessionId) {
        Optional<Session> session = findById(sessionId);
        if (!session.isPresent()) {
            return Optional.empty();
        }
        repository.delete(session.get());
        return session;
    }

}
