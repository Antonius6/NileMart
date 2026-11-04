package org.route;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Governorate {
    Cairo(Zone.GREATER_CAIRO), Giza(Zone.GREATER_CAIRO), Qalyubia(Zone.GREATER_CAIRO),

    Alexandria(Zone.ALEX_DELTA), Beheira(Zone.ALEX_DELTA)
    , KafrElSheikh(Zone.ALEX_DELTA), Sharqia(Zone.ALEX_DELTA)
    ,Dakahlia(Zone.ALEX_DELTA), Damietta(Zone.ALEX_DELTA),
    Gharbia(Zone.ALEX_DELTA), Monufia(Zone.ALEX_DELTA),


    PortSaid (Zone.CANAL), Ismailia(Zone.CANAL), Suez(Zone.CANAL),

    Faiyum(Zone.UPPER_EGYPT), BeniSuef(Zone.UPPER_EGYPT), Minya(Zone.UPPER_EGYPT),
    Asyut(Zone.UPPER_EGYPT), Sohag(Zone.UPPER_EGYPT),
    Qena(Zone.UPPER_EGYPT), Luxor(Zone.UPPER_EGYPT), Aswan(Zone.UPPER_EGYPT),

    Matrouh(Zone.REMOTE), NorthSinai(Zone.REMOTE),
    SouthSinai(Zone.REMOTE), RedSea(Zone.REMOTE),
    NewValley(Zone.REMOTE);

     private final Zone zone;




    public static Governorate fromText(String text) {
        if (text == null) {
            throw new StoreException("Governorate cannot be null");
        }

        String formatted = text.trim().toLowerCase();

        return switch (formatted) {
            case "cairo" -> Cairo;
            case "giza" -> Giza;
            case "qalyubia" -> Qalyubia;

            case "alexandria" -> Alexandria;
            case "beheira" -> Beheira;
            case "kafrelsheikh" -> KafrElSheikh;
            case "sharqia" -> Sharqia;
            case "dakahlia" -> Dakahlia;
            case "damietta" -> Damietta;
            case "gharbia" -> Gharbia;
            case "monufia" -> Monufia;

            case "portsaid" -> PortSaid;
            case "ismailia" -> Ismailia;
            case "suez" -> Suez;

            case "faiyum" -> Faiyum;
            case "benisuef" -> BeniSuef;
            case "minya" -> Minya;
            case "asyut" -> Asyut;
            case "sohag" -> Sohag;
            case "qena" -> Qena;
            case "luxor" -> Luxor;
            case "aswan" -> Aswan;

            case "matrouh" -> Matrouh;
            case "northsinai" -> NorthSinai;
            case "southsinai" -> SouthSinai;
            case "redsea" -> RedSea;
            case "newvalley" -> NewValley;

            default -> throw new StoreException("Invalid governorate: " + text);
        };
    }


    }
