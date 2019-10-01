package mylotte_backend

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class LotServiceSpec extends Specification {

    LotService lotService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Lot(...).save(flush: true, failOnError: true)
        //new Lot(...).save(flush: true, failOnError: true)
        //Lot lot = new Lot(...).save(flush: true, failOnError: true)
        //new Lot(...).save(flush: true, failOnError: true)
        //new Lot(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //lot.id
    }

    void "test get"() {
        setupData()

        expect:
        lotService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Lot> lotList = lotService.list(max: 2, offset: 2)

        then:
        lotList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        lotService.count() == 5
    }

    void "test delete"() {
        Long lotId = setupData()

        expect:
        lotService.count() == 5

        when:
        lotService.delete(lotId)
        sessionFactory.currentSession.flush()

        then:
        lotService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Lot lot = new Lot()
        lotService.save(lot)

        then:
        lot.id != null
    }
}
