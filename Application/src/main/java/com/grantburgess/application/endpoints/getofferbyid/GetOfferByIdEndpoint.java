package com.grantburgess.application.endpoints.getofferbyid;

import com.grantburgess.application.endpoints.BaseEndpoint;
import com.grantburgess.presenters.OfferOutputBoundary;
import com.grantburgess.presenters.OfferViewModel;
import com.grantburgess.usecases.get.OfferResponse;
import com.grantburgess.usecases.get.offerbyid.GetOfferByIdInputBoundary;
import com.grantburgess.usecases.get.offerbyid.GetOfferRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/offers")
public class GetOfferByIdEndpoint implements BaseEndpoint {
    private final GetOfferByIdInputBoundary useCase;
    private final OfferOutputBoundary presenter;

    public GetOfferByIdEndpoint(GetOfferByIdInputBoundary useCase, OfferOutputBoundary presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    @GetMapping("/{offerId}")
    public ResponseEntity execute(@PathVariable(value = "offerId") String offerId) {
        OfferResponse responseModel = useCase.execute(GetOfferRequest.builder().id(UUID.fromString(offerId)).build());
        if (responseModel == null)
            return ResponseEntity.notFound().build();

        presenter.present(responseModel);

        return ResponseEntity.ok(presenter.getViewModel());
    }
}