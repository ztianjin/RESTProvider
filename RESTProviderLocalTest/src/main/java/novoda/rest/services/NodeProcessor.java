package novoda.rest.services;

import novoda.rest.context.CallInfo;
import novoda.rest.parsers.Node;

public interface NodeProcessor {
    
    /*
     * process the node
     */
    public void process(Node<?> node, CallInfo context);
}
