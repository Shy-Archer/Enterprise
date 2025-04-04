package lab.requests.backing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.html.HtmlDataTable;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Size;
import lab.requests.data.RequestRepository;
import lab.requests.entities.Request;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class RequestsList {
    @Inject
    private RequestRepository requestRepository;

    @Size(min = 3, max = 60, message = "{request.size}")
    private String newRequest;

    private HtmlDataTable requestsDataTable;

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }


    public String getNewRequest() {
        return newRequest;
    }

    public void setNewRequest(String newRequest) {
        this.newRequest = newRequest;
    }

    public HtmlDataTable getRequestsDataTable() {
        return requestsDataTable;
    }

    public void setRequestsDataTable(HtmlDataTable requestsDataTable) {
        this.requestsDataTable = requestsDataTable;
    }
    @Transactional
    public String addRequest() {
        Request request = new Request();  // Tworzenie nowej instancji encji
        request.setRequestDate(LocalDate.now()); // Ustawienie bieżącej daty
        request.setRequestText(newRequest); // Pobranie tekstu z formularza

        requestRepository.create(request); // Zapis do bazy danych

        setNewRequest(""); // Czyszczenie pola po dodaniu

        return "requestsList?faces-redirect=true"; // Zostajemy na tej samej stronie
    }
    @Transactional
    public String deleteRequest() {
        Request req =(Request) getRequestsDataTable().getRowData();
        requestRepository.remove(req);
        return "requestsList?faces-redirect=true";
    }
}
