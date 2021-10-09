import React from "react";
import { TextContent, Title } from "@patternfly/react-core";

import rhtLogo from "../images/training_black.png";

export function Welcome() {
    return (
        <>
            <Title headingLevel="h1" size="4xl" style={{ textAlign: "center" }}>
                Welcome to the currency exchange application!
            </Title>

            <div style={{ textAlign: "center" }}>
                <img src={rhtLogo} alt="Red Hat Training Logo" />
            </div>

            <TextContent>
                <p>Please choose one of the two links on the left:</p>
                <ul>
                    <li>
                        <em>Historical Data</em> for seeing currency&apos;s
                        exchange rate in time
                    </li>
                    <li>
                        <em>Exchange</em> for seeing how much money is your
                        amount worth in a different currency
                    </li>
                </ul>
            </TextContent>
        </>
    );
}
