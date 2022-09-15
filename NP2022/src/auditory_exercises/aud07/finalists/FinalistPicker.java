package auditory_exercises.aud07.finalists;

import java.util.*;
import java.util.stream.Collectors;

public class FinalistPicker {
    private int finalists;
    private static Random RANDOM = new Random();

    public FinalistPicker(int f) {
        finalists = f;
    }


    public Collection<Integer> pick(int prizes) throws InvalidArgumentException {

        if (prizes <= 0) throw new InvalidArgumentException("Prizes number must be positive!");

        if (prizes > finalists) throw new
                InvalidArgumentException(String.format("Cannot divide %d prizes to %d finalists", prizes, finalists));

        /**
         * Solution with List
         */
        List<Integer> result = new ArrayList<>();
        while (result.size() != prizes) {
            int pick = RANDOM.nextInt(finalists) + 1;
            if (!result.contains(pick)) {
                result.add(pick);
            }
        }


        /**
         * Solution with Set
         */
        Set<Integer> set = new HashSet<>();
        while (set.size() != prizes) {
            int pick = RANDOM.nextInt(finalists) + 1;
            set.add(pick);
        }

        /**
         * Solution with streams
         */
        return RANDOM.ints(2 * prizes, 1, finalists)
                .distinct()
                .limit(prizes)
                .boxed()
                .collect(Collectors.toList());
    }
}
