
package org.onebeartoe.web.search;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * This interface provides a contract for searching the Web.
 */
public interface WebSearch
{
    List<SearchResult> search(SearchRequest request) throws GeneralSecurityException, IOException;
}
