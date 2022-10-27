package explorewithme.ewm.requests;

import explorewithme.ewm.requests.dto.ParticipationRequestDto;
import explorewithme.ewm.requests.model.Request;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {

    public static ParticipationRequestDto fromRequest(Request request){
        ParticipationRequestDto requestDto = new ParticipationRequestDto(
               request.getCreated(),
        request.getEvent(),
        request.getId(),
        request.getRequester(),
        request.getStatus());
        return requestDto;
    }

}
