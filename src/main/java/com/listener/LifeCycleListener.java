package com.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * Created by szkolenie on 2016-08-17.
 */
public class LifeCycleListener implements PhaseListener {


    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
        System.out.println("END PHASE " + phaseEvent.getPhaseId());
    }

    @Override
    public void beforePhase(PhaseEvent phaseEvent) {
        System.out.println("START PHASE " + phaseEvent.getPhaseId());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}
