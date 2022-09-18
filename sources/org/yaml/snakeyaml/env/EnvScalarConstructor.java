package org.yaml.snakeyaml.env;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jdk.internal.dynalink.CallSiteDescriptor;
import org.apache.log4j.spi.LocationInfo;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.MissingEnvironmentVariableException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/env/EnvScalarConstructor.class */
public class EnvScalarConstructor extends Constructor {
    public static final Tag ENV_TAG = new Tag("!ENV");
    public static final Pattern ENV_FORMAT = Pattern.compile("^\\$\\{\\s*((?<name>\\w+)((?<separator>:?(-|\\?))(?<value>\\S+)?)?)\\s*\\}$");

    public EnvScalarConstructor() {
        this.yamlConstructors.put(ENV_TAG, new ConstructEnv());
    }

    /* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/env/EnvScalarConstructor$ConstructEnv.class */
    private class ConstructEnv extends AbstractConstruct {
        private ConstructEnv() {
            EnvScalarConstructor.this = r4;
        }

        @Override // org.yaml.snakeyaml.constructor.Construct
        public Object construct(Node node) {
            String val = EnvScalarConstructor.this.constructScalar((ScalarNode) node);
            Matcher matcher = EnvScalarConstructor.ENV_FORMAT.matcher(val);
            matcher.matches();
            String name = matcher.group("name");
            String value = matcher.group("value");
            String separator = matcher.group("separator");
            return EnvScalarConstructor.this.apply(name, separator, value != null ? value : "", EnvScalarConstructor.this.getEnv(name));
        }
    }

    public String apply(String name, String separator, String value, String environment) {
        if (environment == null || environment.isEmpty()) {
            if (separator != null) {
                if (separator.equals(LocationInfo.f402NA) && environment == null) {
                    throw new MissingEnvironmentVariableException("Missing mandatory variable " + name + ": " + value);
                }
                if (separator.equals(":?")) {
                    if (environment == null) {
                        throw new MissingEnvironmentVariableException("Missing mandatory variable " + name + ": " + value);
                    }
                    if (environment.isEmpty()) {
                        throw new MissingEnvironmentVariableException("Empty mandatory variable " + name + ": " + value);
                    }
                }
                if (separator.startsWith(CallSiteDescriptor.TOKEN_DELIMITER)) {
                    if (environment == null || environment.isEmpty()) {
                        return value;
                    }
                    return "";
                } else if (environment == null) {
                    return value;
                } else {
                    return "";
                }
            }
            return "";
        }
        return environment;
    }

    public String getEnv(String key) {
        return System.getenv(key);
    }
}
