package mylotte_backend

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class PackingServiceSpec extends Specification {

    PackingService packingService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Packing(...).save(flush: true, failOnError: true)
        //new Packing(...).save(flush: true, failOnError: true)
        //Packing packing = new Packing(...).save(flush: true, failOnError: true)
        //new Packing(...).save(flush: true, failOnError: true)
        //new Packing(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //packing.id
    }

    void "test get"() {
        setupData()

        expect:
        packingService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Packing> packingList = packingService.list(max: 2, offset: 2)

        then:
        packingList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        packingService.count() == 5
    }

    void "test delete"() {
        Long packingId = setupData()

        expect:
        packingService.count() == 5

        when:
        packingService.delete(packingId)
        sessionFactory.currentSession.flush()

        then:
        packingService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Packing packing = new Packing()
        packingService.save(packing)

        then:
        packing.id != null
    }
}
